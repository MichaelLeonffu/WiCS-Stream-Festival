from graphics import *
import time
import numpy
import random


window = GraphWin("Gravity", 500, 500)

# room
box = Rectangle(Point(1, 1), Point(499, 499))
box.draw(window)

#text
text = Text(Point(50, 50), "Wics")
text.draw(window)

ball = Circle(Point(50, 50), 10)
ball.setFill("pink")
ball.draw(window)

print ball.getCenter().getX()
print ball.getCenter().getY()
print ball.getRadius()

ball.move(1, 1)
print ball

pixelsPerMeter = 100.0

#in jasmines

weight = 3.14

# meters/time

xVel = 5.0
yVel = 0.0

xAcl = 0.0
yAcl = 9.81

# force as N

airResistance = 0.009 # as v/v^2

# F = ma; 

lastTime = time.time()

xNoMotionCount = 0
yNoMotionCount = 0

for x in range(0, 900):
	framesPerSec = 1.0/60.0

	print x

	#action
	if abs(xVel) < 0.5:
		xNoMotionCount += 1
	else:
		xNoMotionCount = 0

	if xNoMotionCount > 20:
		xVel = 20 * random.random() - 10

	if abs(yVel) < 0.5:
		yNoMotionCount += 1
	else:
		yNoMotionCount = 0

	if yNoMotionCount > 20:
		yVel = 20 * random.random() - 10

	#phys
	xVel += xAcl * framesPerSec
	yVel += yAcl * framesPerSec

	#air resistance
	xVel += -numpy.sign(xVel) * xVel * xVel * airResistance;
	yVel += -numpy.sign(yVel) * yVel * yVel * airResistance;

	ball.move(xVel * framesPerSec * pixelsPerMeter, yVel * framesPerSec * pixelsPerMeter)
	text.move(ball.getCenter().getX() - text.getAnchor().getX(), ball.getCenter().getY() - text.getAnchor().getY() - 15)

	#colisions
	if ball.getCenter().getX() - ball.getRadius() < 0 or ball.getCenter().getX() + ball.getRadius() > 500:
		xVel *= -0.57 # restoring force
		xDisplacement = ball.getCenter().getX() - ball.getRadius() if ball.getCenter().getX() - ball.getRadius() < 0 else ball.getCenter().getX() + ball.getRadius() - 500
		ball.move(-(xDisplacement + 1), 0)

	if ball.getCenter().getY() - ball.getRadius() < 0 or ball.getCenter().getY() + ball.getRadius() > 500:
		yVel *= -0.57 # restoring force
		yDisplacement = ball.getCenter().getY() - ball.getRadius() if ball.getCenter().getY() - ball.getRadius() < 0 else ball.getCenter().getY() + ball.getRadius() - 500
		ball.move(0, -(yDisplacement+1))

	while time.time() - lastTime < framesPerSec:
		pass

	lastTime = time.time();

time.sleep(1)

window.close()