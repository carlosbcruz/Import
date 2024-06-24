const axios = require('axios');

const url = 'http://localhost:8000';
const callsPerSecond = 10;
const interval = 1000 / callsPerSecond;

const makeRequest = async () => {
  try {
    const response = await axios.get(url);
    console.log(`Status: ${response.status}, Data: ${response.data}`);
  } catch (error) {
    console.error(`Erro: ${error}`);
  }
};

setInterval(makeRequest, interval);