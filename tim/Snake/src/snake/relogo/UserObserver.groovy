package snake.relogo

import static repast.simphony.relogo.Utility.*;
import static repast.simphony.relogo.UtilityG.*;
import repast.simphony.relogo.BaseObserver;
import repast.simphony.relogo.Stop;
import repast.simphony.relogo.Utility;
import repast.simphony.relogo.UtilityG;

/**
 * A simple version of the classic arcade game Snake.
 * 
 * @author Tim Sweda
 *
 */

class UserObserver extends BaseObserver{

	def setup() {
		clearAll()
		// Initialize turn schedule
		turnSchedule = []
		// Specify colors
		obstacleColor = black()
		foodColor = green()
		neutralColor = white()
		// Reset score
		score = 0
		// Create a boundary around the perimeter of the playing area
		ask (patches()) {
			if (abs(pxcor) < (worldWidth()-1)/2 && abs(pycor) < (worldHeight()-1)/2) {
				pcolor = neutralColor
			}
			else {
				pcolor = obstacleColor
			}
		}
		// Create snake's head
		createHeads(1) {
			color = obstacleColor+3
			heading = 0
			size = 1.5
		}
		headPatch = patch(0, 0)
		// Create snake's body
		body = []
		body = lput(patch(0, -4), lput(patch(0, -3), lput(patch(0, -2), lput(patch(0, -1), body))))
		ask (body) {
			pcolor = obstacleColor
		}
		// Place food on a random patch
		ask (oneOf(patches().with({pcolor == neutralColor && count(headsHere()) == 0}))) {
			pcolor = foodColor
		}
	}
	
	def go() {
		ask (heads()) {
			def headPcolor = {pcolor}.of(headPatch)
			// If snake is scheduled to turn, turn then update schedule
			if (count(turnSchedule) > 0) {
				right(first(turnSchedule))
				turnSchedule = butFirst(turnSchedule)
			}
			// Move snake forward
			forward(1)
			// If snake eats food, increment player's score and create a new food patch elsewhere
			if (getPcolor() == foodColor) {
				setPcolor(foodColor+4)
				score++
				ask (oneOf(patches().with({pcolor == neutralColor}))) {
					pcolor = foodColor
				}
			}
			// Update snake's body
			ask (headPatch) {
				pcolor = obstacleColor
			}
			body = fput(headPatch, body)
			headPatch = patchHere()
			// If snake just ate food, increase its body's length by 1
			if (headPcolor != foodColor+4) {
				ask (last(body)) {
					pcolor = neutralColor
				}
				body = butLast(body)
			}
			// If snake ran into an obstacle, then game over
			if ({pcolor}.of(headPatch) == obstacleColor) {
				setShape("x")
				userMessage("Game Over")
				observerStop()
			}
		}
		// Delay until next tick (becomes shorter as difficulty increases)
		wait(0.2-difficulty/60)
	}
	
	// Turn snake left
	def turnLeft() {
		turnSchedule = lput(-90, turnSchedule)
	}
	
	// Turn snake right
	def turnRight() {
		turnSchedule = lput(90, turnSchedule)
	}
	
	// Return player's score
	def myScore() {
		return score
	}

}