import socket
import requests


class HelloBot:
  sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

  def send_msg(self, msg: str):
    self.sock.send(bytes(msg, 'utf-8'))

  def __init__(self):

    self.tmp_dic = {
      "hello": "Hello World",
      "test": "123",
      "chucky": self.get_chucky
    }

    self.data_service_address = "NEEDS HOST IP"
    self.sock.connect(("chatserver", 8080))

    while True:
      data = self.sock.recv(1024)
      if not data:
        break
      tmp_data = str(data, 'utf-8')
      if tmp_data in self.tmp_dic:
        if callable(self.tmp_dic[tmp_data]):
          tmp_msg = self.tmp_dic[tmp_data].__call__().text
        else:
          tmp_msg = self.tmp_dic[tmp_data]
        self.send_msg(tmp_msg)

  def get_chucky(self):
    request_data = requests.get(
        "http://{}:9090/chucky".format(self.data_service_address))
    return request_data


client = HelloBot()
