# FileAuth Example Plugin

This authorization plugin is based on HTTP basic authentication.
Authentication is performed against passwd file.

## Usage

See the [plugin configuration file](src/main/resources/plugin-conf.json).
It allows access if username and password are the same with this permissions:

- Creation is always allowed
- View is always allowed
- Update is allowed only, if the username is ernie
- Deletion is allowed only on path /test (recursively)
- KillTask is not allowed
