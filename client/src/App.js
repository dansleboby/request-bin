import React, {useEffect} from 'react';
import './App.scss';
import Controls from "./components/Controls/Controls";

function App() {

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8080/test/stream');
        eventSource.onmessage = e => console.log(e);
    }, []);

    return (
        <section className="section">
            <div className="container">
                <h1 className="title">
                    Request Bin
                </h1>
                <Controls/>
                <hr/>
            </div>
        </section>
    );
}

export default App;
