import React, {useEffect} from 'react';
import './App.scss';
import Controls from "./components/controls/Controls";
import ControlPanel from "./containers/ControlPanel/ControlPanel";

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
                <ControlPanel/>
            </div>
        </section>
    );
}

export default App;
