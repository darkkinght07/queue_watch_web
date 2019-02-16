# Queue Watch
My custom solution to manage activemq queues and messages through a web interface

For the beginning only ActiveMQ will be supported, with RabbitMQ following.
To start the app add -Dspring.profiles.active=dev,activemq as VM arguments.

api:
/queue/info - display the details for a particular queue
/queue/push - pushing messages to a queue
/queue/pull - consuming all messages from a queue

Enjoy!
