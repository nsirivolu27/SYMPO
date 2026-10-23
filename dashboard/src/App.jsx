import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";

const API_BASE = "http://localhost:8081/api";

export function App() {
  const [sponsors, setSponsors] = useState([]);
  const [creators, setCreators] = useState([]);
  const [matches, setMatches] = useState([]);
  const [activity, setActivity] = useState([]);

  useEffect(() => {
    loadData();

    const client = new Client({
      brokerURL: "ws://localhost:8081/ws",
      reconnectDelay: 2000,
      onConnect: () => {
        client.subscribe("/topic/activity", (message) => {
          const event = JSON.parse(message.body);
          setActivity((current) => [event, ...current].slice(0, 12));
        });

        client.subscribe("/topic/matches", (message) => {
          const liveMatches = JSON.parse(message.body);
          setMatches(liveMatches);
        });
      }
    });

    client.activate();
    return () => client.deactivate();
  }, []);

  async function loadData() {
    const [sponsorRes, creatorRes, matchRes] = await Promise.all([
      fetch(`${API_BASE}/sponsors`),
      fetch(`${API_BASE}/creators`),
      fetch(`${API_BASE}/matches`)
    ]);

    setSponsors(await sponsorRes.json());
    setCreators(await creatorRes.json());
    setMatches(await matchRes.json());
  }

  async function generateMatches(sponsorId) {
    const response = await fetch(`${API_BASE}/sponsors/${sponsorId}/match`, {
      method: "POST"
    });
    const liveMatches = await response.json();
    setMatches(liveMatches);
  }

  return (
    <div className="page">
      <header className="hero">
        <div>
          <p className="eyebrow">Real-Time Sponsorship Marketplace</p>
          <h1>SYMPO</h1>
          <p className="lede">
            Pair sponsors with creators using weighted profile matching and
            monitor marketplace activity in real time.
          </p>
        </div>
        <div className="hero-card">
          <span>Marketplace Snapshot</span>
          <strong>{matches.length}</strong>
          <p>active top matches on screen</p>
        </div>
      </header>

      <main className="grid">
        <section className="panel">
          <div className="panel-header">
            <h2>Sponsors</h2>
            <span>{sponsors.length} loaded</span>
          </div>
          <div className="list">
            {sponsors.map((sponsor) => (
              <button
                key={sponsor.id}
                className="card action-card"
                onClick={() => generateMatches(sponsor.id)}
              >
                <strong>{sponsor.brandName}</strong>
                <span>{sponsor.targetCategory} in {sponsor.targetRegion}</span>
                <small>
                  Min audience {sponsor.minAudienceSize.toLocaleString()} | Min
                  engagement {sponsor.minEngagementRate}%
                </small>
              </button>
            ))}
          </div>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Creators</h2>
            <span>{creators.length} loaded</span>
          </div>
          <div className="list">
            {creators.map((creator) => (
              <article key={creator.id} className="card">
                <strong>{creator.displayName}</strong>
                <span>{creator.primaryCategory} | {creator.region}</span>
                <small>
                  {creator.audienceSize.toLocaleString()} audience | {creator.engagementRate}% engagement
                </small>
              </article>
            ))}
          </div>
        </section>

        <section className="panel wide">
          <div className="panel-header">
            <h2>Top Matches</h2>
            <span>live scoring</span>
          </div>
          <div className="table">
            <div className="table-head">
              <span>Sponsor</span>
              <span>Creator</span>
              <span>Score</span>
            </div>
            {matches.map((match) => (
              <div className="table-row" key={match.matchId ?? `${match.sponsorId}-${match.creatorId}`}>
                <span>{match.sponsorName}</span>
                <span>{match.creatorName}</span>
                <span>{Math.round(match.score)}</span>
              </div>
            ))}
          </div>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Live Activity</h2>
            <span>WebSocket events</span>
          </div>
          <div className="activity">
            {activity.length === 0 ? (
              <p className="empty">Generate matches to see the live feed.</p>
            ) : (
              activity.map((event, index) => (
                <article key={`${event.createdAt}-${index}`} className="activity-item">
                  <strong>{event.type}</strong>
                  <p>{event.message}</p>
                </article>
              ))
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

