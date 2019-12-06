class Executer:
    def __init__(self, tcpServer):
        self.andRaspTCP = tcpServer

    def startCommand(self, command):
        if command == "123\n":
            print("we send.")
            self.andRaspTCP.sendAll("321\n")


