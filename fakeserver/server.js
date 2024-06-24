const express = require('express');
const app = express();
const port = 8000;

let requestCount = 0;
let startTime = Date.now();

app.post('/', (req, res) => {
  requestCount++;
  res.send('Endpoint de teste');
});

setInterval(() => {
  const currentTime = Date.now();
  const elapsedSeconds = (currentTime - startTime) / 1000;
  const requestsPerSecond = (requestCount / elapsedSeconds).toFixed(2);

  console.log(`Chamadas por segundo: ${requestsPerSecond}`);
  
  // Resetar contador e tempo
  requestCount = 0;
  startTime = currentTime;
}, 1000);

app.listen(port, () => {
  console.log(`Servidor rodando em http://localhost:${port}`);
});