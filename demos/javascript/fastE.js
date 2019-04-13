
readline = require('readline')
rl = readline.createInterface({input: process.stdin, output: process.stdout})

rl.question('Message: ', (answer)=>{
	message = answer
	rl.question('Key: should be unique; must be 2-36 range: ', (answer)=>{
		key = answer //key.length is radix must be atleast 2
		
		console.log("Message:", message)
		console.log("Key:", key)

		console.log("Generating space")
		var space = 0
		for(i = 2; i < 32; i++)
			if(128.0/Math.pow(key.length, i) < 1){
				space = i
				break
			}
		console.log("Space: ", space)

		finalMessageParts = []
		for(i = 0; i < message.length; i++)
			finalMessageParts.push(message.charCodeAt(i))

		function format(s, l){
			while(s.length < l)
				s = '0' + s
			return s
		}

		finalMessage = ''

		for(i = 0; i < finalMessageParts.length; i++)
			finalMessageParts[i] = format(finalMessageParts[i].toString(key.length), space)

		for(i = 0; i < finalMessageParts.length; i++)
			for(j = 0; j < finalMessageParts[i].length; j++)
				finalMessage += key.charAt(finalMessageParts[i][j])

		if(true) //split into fours
			for(i = space; i < finalMessage.length; i+= space+1)
				finalMessage = finalMessage.substring(0, i) + " " + finalMessage.substring(i, finalMessage.length)

		console.log(finalMessage)

		rl.close()
	})
})