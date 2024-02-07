import os

def main():
    print("ENV_FOO", os.environ.get("ENV_FOO"))
    print("ENV_BAR", os.environ.get("ENV_BAR"))

if __name__ == '__main__':
    main()

