import socket


class Client:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tmp_dic = {
        "hello": "Hello World",
        "test": "123",
        "chuck": "Chuck Norris could use anything in java.util.* to kill you, including the javadocs.",
        "chuck2": "All arrays Chuck Norris declares are of infinite size, because Chuck Norris knows no bounds.",
        "chuck3": "Chuck Norris doesn't use reflection, reflection asks politely for his help.",
        "hunger": "burger"
    }

    def sendMsg(self, msg: str):
        self.sock.send(bytes(msg, 'utf-8'))

    def __init__(self):
        self.sock.connect(("chatserver", 8080))

        while True:
            data = self.sock.recv(1024)
            if not data:
                break
            tmp_data = str(data, 'utf-8')
            if tmp_data in self.tmp_dic:
                self.sendMsg(self.tmp_dic[tmp_data])


client = Client()
