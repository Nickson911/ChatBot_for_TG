# Telegram Bot with Gemini AI

This is a Telegram bot that uses Google's Gemini AI for generating responses.

## Deployment to Render.com

1. Create a Render account at https://render.com
2. Fork this repository to your GitHub account
3. In Render dashboard:
   - Click "New +"
   - Select "Web Service"
   - Connect your GitHub repository
   - Choose the repository with the bot

4. Configure the service:
   - Name: your-bot-name
   - Environment: Docker
   - Branch: main
   - Root Directory: ./
   - Instance Type: Free

5. Add environment variables:
   - BOT_USERNAME=your_bot_username
   - BOT_TOKEN=your_bot_token
   - GEMINI_API_KEY=your_gemini_api_key

6. Click "Create Web Service"

The bot will be automatically deployed and will run 24/7 on Render's free tier.

Note: Free tier has some limitations:
- Spins down after 15 minutes of inactivity
- Spins up automatically when receiving a request
- 512 MB RAM
- Shared CPU

## Local Development

1. Set environment variables in `application.properties`
2. Run the application:
```bash
mvn spring-boot:run
```

## Environment Variables

- `BOT_USERNAME`: Telegram bot username
- `BOT_TOKEN`: Telegram bot token
- `GEMINI_API_KEY`: Google Gemini API key
