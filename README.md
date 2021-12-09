# OperationsAkka-CS554
### Group Members
| Name                   | Email           | UIN       | 
|------------------------|-----------------|-----------|
| Parth Deshpande        | pdeshp8@uic.edu | 657711378 |
| Fatema Engineeringwala | fengin2@uic.edu | 675589901 | 
| Mallika Patil          | mpatil5@uic.edu | 660213398 |

## Brief Project Description 
Actor-based computational model by implementing a configurable generator of Actor-based applications using Scala or Java based on Akka actorsand you will build and run your project using the SBT with the runMain command from the command line.

## Implementation Details 
Implementation of Akka actors for a particular use case using Scala. Input YAML file including the list of actors, the list of messages with their fields that these actors exchange and some basic Scala or Java code that is dynamically injected into the corresponding actors that performs some manipulations of the received messages and generates and sends new messages to other actors. 

## Instructions on Running Code 
`sbt compile` used to compile the code<br>
`~run` used to begin running the program

## Use Case
1. Orders created for Processor for a Phone Factory. 
2. Random number of Orders are created for Processing. Each order contains a random number of Phone Model 1, Phone Model 2, or Phone Model 3. 
3. Each phone has a specific configuration with a body labeled B1, B2, or B3, and a camera, C1, and a processor, P1. 
4. Each order get processed and all information is logged in a logfile.

## Implementation
1. OrderCreator class is responsible for random order generation and sending this order message to OrderProcessor
2. OrderProcessor sends each order type to the PhoneManufacturer and expects a Manufactured phone in return
3. PhoneManufacturer manufactures phone as it receives order from OrderProcessor
4. Input.yaml file stores the list or actor and its messages as scala code
5. Snapshot.yaml file stores the actor's state periodically with a fixed delay of 5 seconds

## Examples
### Order Creation
Consists of a random number of Phone 1, Phone 2, and Phone 3. 
![image](https://user-images.githubusercontent.com/55963699/144965457-e2db09a0-ca33-437d-8d05-051a666e17cf.png)
### Example of Phone Body
Similar to the creation of B2, B3, C1, and P1.<br>
<img src="https://user-images.githubusercontent.com/55963699/144965652-f40edac4-5b4a-45e9-98bb-a5c6080461b1.png" height="100px"/> 

### Phone Class
Each phone is made of a different body, camera, and processor.<br>
<img src="https://user-images.githubusercontent.com/55963699/144966275-cfc2c428-53aa-4f22-bc94-3bc4717f0d6c.png" height="100px"/>

### Example of Running the Code
<img src="https://user-images.githubusercontent.com/55963699/144966544-c81b94f4-97ba-40fa-8254-e0c269779f24.png" height="150px"/>

