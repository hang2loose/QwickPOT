import socket


class Client:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def sendMsg(self, msg: str):
        self.sock.send(bytes(msg, 'utf-8'))

    def __init__(self):
        self.sock.connect(("127.0.0.1", 8080))

        while True:
            data = self.sock.recv(1024)
            if not data:
                break
            tmp_data = str(data, 'utf-8')
            if tmp_data == "hello":
                self.sendMsg("hello world")
            if tmp_data == "test":
                self.sendMsg("123")


client = Client()
