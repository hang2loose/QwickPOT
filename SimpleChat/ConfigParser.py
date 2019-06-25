import yaml

class ConfigParser:
    def __init__(self, filename):
        try:
            with open(filename, 'r') as ymlfile:
                cfg = yaml.load(ymlfile, Loader=yaml.FullLoader)
                self.__cfg = cfg
        except FileNotFoundError:
            print("Can't find or open file, using default (0.0.0.0:8080)")
            self.cfg = {'config': {'address': '0.0.0.0', 'port': 8080}}

    def get_config(self):
        return self.__cfg
