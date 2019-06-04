import requests

class HelloBot:

  def __init__(self, service_address: str):
    self.data_service_address = service_address

    self.tmp_dic = {
      "hello": "Hello World",
      "test": "123",
      "chucky": self.get_chucky
    }

  def get_msg(self, msg):
    if msg in self.tmp_dic:
      if callable(self.tmp_dic[msg]):
        return self.tmp_dic[msg].__call__().text
      else:
        return self.tmp_dic[msg]

  def get_chucky(self):
    request_data = requests.get(
        "http://{}:9090/chucky".format(self.data_service_address))
    return request_data
