#Read the comments on client.py first, they are more descriptive

import socket
import os                

# next create a socket object 
s = socket.socket()          
print ("Socket successfully created")
port_parse = input("Enter port number: ")
port = int(port_parse)                
  
# Next bind to the port 
# we have not typed any ip in the ip field 
# instead we have inputted an empty string 
# this makes the server listen to requests  
# coming from other computers on the network 
s.bind(('', port))         
print ("socket binded to " + str(port)) 
  
# put the socket into listening mode 
s.listen(5)      
print ("socket is listening")            
  
# a forever loop until we interrupt it or  
# an error occurs 
while True: 
  
   # Establish connection with client. 
   c, addr = s.accept()      
   print ('Got connection from', addr) 
  
   # send a thank you message to the client.  
   c.sendall(bytes('Connection established.', 'utf-8'))
   #Receive command type from client, tells us what method to run
   while(True) :
      command = (c.recv(1024)).decode('utf-8')
      if(command == "d") :
         clientDownload(c)
      elif(command == "c") :
         close(c)
         #break
      elif(command == "u") :
         clientUpload(c)
      elif(command == "s") :
         returnFiles(c)
      else :
         print()
  
   # Close the connection with the client 
   exit()