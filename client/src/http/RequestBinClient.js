import axios from 'axios';

const requestBinClient = axios.create({
    baseURL: 'http://localhost:8080'
});

export default requestBinClient;
