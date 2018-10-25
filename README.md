# BasicDatacenterSim
A basic framework for a datacenter written with synchronous webapp architecture. Uses Java Vertx and is multilayered.


Here's a quick run-down on what this datacenter looks like: There are two people (clients) trying to connect to a web service, like a website. One guy is a regular user while the other is a really mean hacker. The datacenter is primarily composed of two layers of data -- the "Server Layer", which currently hosts 2 servers, each responding to the requests of one client, and the "Data Layer" which is where a centralized data storage system is kept (this could be something as common as a SAN). Yes, I made these terms up just for the sake of explanation.


Here's how you can visualize this


                        Client 1                    Client 2 (BAD GUY HACKER)
                            |                           |
                            |                           |
                            |                           |
                      -=-=-=-=-=-=-+|First layer|+-=-=-=-=-=-=-
                           |                           |
                           |                           |
    "Server Layer"      Server 1                    Server 2
                            \                         /
                             \                       /
                              \                     /
                               \                   /
                                \                 /
    "Data Layer"         Centralized Storage (a SAN, for example)
   
   
   
   
   Lets imagine this system in action: Client 1 wants to visit a website such as Youtube.com, which holds lots of data. When the client's request is sent through, it is first recieved by Server 1. This server can host the HTML of Youtube.com, but does not host the actual data that is on Youtube's front page. To retrieve this data for the Client, Server 1 automatically consults the Centralized Storage to get some data. Once this data is recieved, it is then re-routed back through Server 1, and finally to Client 1, completing the round trip of our client's initial request and providing them with the services they need. A rather noisy client, however, such as Client 2, may be trying to attack the Centralized Storage by means of a DoS attack or something similar to that. Client 2 would use the same kind of requests as Client 1, but with greater intensity (thousands of requests per second, for example).
   
   In my code, Server 1 and Server 2 do not host any HTML pages, and the Centralized Storage computes a random number instead of retrieving a file. Tinkering my code to apply my setup to the aforementioned scenario, however, is easy to do.
