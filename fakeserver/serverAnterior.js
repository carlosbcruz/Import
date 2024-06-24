const http = require('http');

var calls = 0;
var firstCall = 0;

// Creamos una función que nos maneje las request y responses
const handleRequest = (req, res) => {
  if (firstCall == 0) {
    firstCall = Date.now()
    res.end('Iniciou'); 
  } else {
    milliseconds = (Date.now() - firstCall)
    if (milliseconds > 1000) {
      firstCall = Date.now()
      mensagem = ' Chamadas por segundo: ' + (calls / milliseconds * 1000) 
      calls = 0;
      res.end(mensagem); // usando el response podemos enviar un mensaje al browser
    } else {
      res.end('Chamadas ' + ++calls + ' - ' + (calls / milliseconds * 1000)); // usando el response podemos enviar un mensaje al browser
    }
  }
};

// Creamos el servidos gracias al metodo .createServer() del metodo 'http' y le pasamos como parametro una callback function
const server = http.createServer(handleRequest);

const port = 8000;

// Hacemos que el servidor se aloje en el puerto 8000 de nuestra computadora
server.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
