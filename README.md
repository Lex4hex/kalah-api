# 6 stone Kalah API

### Description
API for 6 stone Kalah game.
https://en.wikipedia.org/wiki/Kalah

### API specification
Swagger UI http://localhost:8066/swagger-ui.html

- `POST /games/` Creates a new game and persists it to storage. Initializes game board with initial stones
                             in each of the players pits.    
#####Example  
Request:                                                    
```
curl -X POST "http://localhost:8066/games/"
```  
Response:  
```
 {
    "id": "0c43bbde-d276-47b2-b4cd-9026b8b46b4d",
    "uri": "/games/0c43bbde-d276-47b2-b4cd-9026b8b46b4d"
 }
```                            
- `PUT /games/{gameId}/pits/{pitId}` Makes a move. Sows stones from selected pit.
#####Example  
Request:                                                    
```
curl -X PUT "http://localhost:8066/games/0c43bbde-d276-47b2-b4cd-9026b8b46b4d/pits/3" 
```  
Response:  
```
{
  "id": "0c43bbde-d276-47b2-b4cd-9026b8b46b4d",
  "uri": "/games/0c43bbde-d276-47b2-b4cd-9026b8b46b4d",
  "gameStatus": "SECOND_PLAYER_TURN",
  "status": {
    "1": 3,
    "2": 2,
    "3": 0,
    "4": 12,
    "5": 3,
    "6": 10,
    "7": 4,
    "8": 2,
    "9": 10,
    "10": 0,
    "11": 2,
    "12": 11,
    "13": 10,
    "14": 3
  }
}
```  

###Notes
The first turn is determined by the owner of the first pit being sown.  

### Technologies
- Spring Boot
- Slf4j
- Springfox/Swagger
- Maven
- Lombok        
- JUnit
- Mockito

### Build 

Create an executable Spring Boot jar  
`./mvnw clean package spring-boot:repackage`

### Run
`./target/kalah-0.0.1.jar`
   
Application runs on 8066 port. 
