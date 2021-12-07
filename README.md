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
