if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

var ws = new WebSocket("ws://localhost:9000/socket");

ws.onopen = function() {
  alert("Connected!");
};

ws.onmessage = function(msg) {
  alert("Received " + msg);
};

