# Protocol
## General
```
-> connected
register:<user>:<pass> -> register:success
register:<user>:<pass> -> register:failed
login:<user>:<pass> -> login:success:<user>:<user>
login:<user>:<pass> -> login:failed
logout:<user>:<user> -> logout:success
logout:<user>:<user> -> logout:failed
points:<user>:<user> -> points:<amount>
points:<user>:<user> -> points:failed
```

## Logged In
```
challenge:<challenger>:<user>:<user> -> challenge:accepted
challenge:<challenger>:<user>:<user> -> challenge:denied
challenge:<challenger>:<user>:<user> -> challenge:error:timeout
challenge:<challenger>:<user>:<user> -> challenge:error:ratelimited
challenge:<challenger>:<user>:<user> -> challenge:error:offline
challenge:<challenger>:<user>:<user> -> challenge:error:failed
```

## In Game
```
move:<user>:<token>:<row>:<col> -> move:success:<row>:<col>
move:<user>:<token>:<row>:<col> -> move:failed
```

## Client Events
```
request:<challenger> -> accept/deny:<user>:<token>:<challenger>
request:<challenger> -> accept/deny:failed
game:move:<row>:<col>
game:won
game:lose
game:tie
```