import axios from 'axios';

const requestBinClient = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? 'https://bin.graversen.io' : 'http://localhost:8080'
});

export default requestBinClient;
