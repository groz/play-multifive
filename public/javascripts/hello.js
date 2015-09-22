if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

angular.module("GameApp", []).controller("GameController", function($scope) {

  var game = this;

  var ws = new WebSocket("ws://localhost:9000/socket");

  ws.onopen = function() {
    console.log("Connected to server...");
  };

  ws.onmessage = function(msg) {
    var command = JSON.parse(msg.data);
    console.log(command);

    switch (command.commandType) {
      case "Start":
        game.start(command.fieldSize, command.isFirst);
        break;
      case "Move":
        game.myturn = true;
        console.log(command);
        game.field[command.row][command.column] = 2;
        break;
    }

    $scope.$digest();
  };

  game.start = function(size, myturn) {
    game.started = true;
    game.myturn = myturn;
    game.field = new Array(size);
    for (var i = 0; i < size; i++) {
      game.field[i] = new Array(size);
      for (var j = 0; j < size; j++) {
        game.field[i][j] = "0";
      }
    }
  };

  game.fieldClicked = function(nRow, nCol) {
    if (game.myturn) {
      game.myturn = false;
      game.field[nRow][nCol] = 1;
      var command = { commandType: "Move", row: nRow, column: nCol };
      ws.send(JSON.stringify(command));
    }
  };

  return game;
});

