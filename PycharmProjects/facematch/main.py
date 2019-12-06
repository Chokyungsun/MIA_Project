import tcpServer
import executer
from asyncio import Queue
import time

# make public queue
commandQueue = Queue()

# init module
andRaspTCP = tcpServer.TCPServer(commandQueue, "", 35357)
print("TCP set")
andRaspTCP.start()

# set module to executer
commandExecuter = executer.Executer(andRaspTCP)

while True:
    try:
        command = commandQueue.get()
        commandExecuter.startCommand(command)
    except:
        pass

# while True:
#    time.sleep(3)
#    andRaspTCP.sendAll("321\n")


