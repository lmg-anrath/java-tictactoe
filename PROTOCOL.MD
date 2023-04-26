# Protocol
## General
```
-> connected
register:<user>:<pass> -> register:success
register:<user>:<pass> -> register:failed
login:<user>:<pass> -> login:success:<user>:<token>
login:<user>:<pass> -> login:failed
logout:<user>:<token> -> logout:success
logout:<user>:<token> -> logout:failed
points:<user>:<token> -> points:<amount>
```

## Logged In
```
challenge:<challenger>:<token>:<user> -> challenge:accepted
challenge:<challenger>:<token>:<user> -> challenge:denied
challenge:<challenger>:<token>:<user> -> challenge:error:timeout
challenge:<challenger>:<token>:<user> -> challenge:error:ratelimited
challenge:<challenger>:<token>:<user> -> challenge:error:offline
```

## In Game
```
move:<user>:<token>:<row>,<col>
```

## Client Events
```
challenge:<challenger> -> accept:<user>:<token>
challenge:<challenger> -> deny:<user>:<token>
game:move:<row>,<col>
game:won:<winner>
game:lose
game:tie
```