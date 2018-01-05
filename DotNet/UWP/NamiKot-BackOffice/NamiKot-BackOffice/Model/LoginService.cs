using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using Windows.UI.Popups;

namespace NamiKot_BackOffice.Model
{
    public class LoginService
    {
        public async Task<string> GetToken(Login login)
        {
            var clientLogin = new HttpClient();
            clientLogin.BaseAddress = new Uri("http://namikot2.azurewebsites.net/");
            clientLogin.DefaultRequestHeaders.Accept.Clear();
            clientLogin.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            clientLogin.DefaultRequestHeaders.AcceptEncoding.Add(new StringWithQualityHeaderValue("UTF-8"));  // --> UTF-8 passe

            try
            {
                JObject jObject = JObject.Parse(JsonConvert.SerializeObject(login));
                HttpContent content = new StringContent(jObject.ToString(), Encoding.UTF8, "application/json");
                HttpResponseMessage response = await clientLogin.PostAsync("/api/jwt", content);

                if (response != null)
                {
                    var statusCode = response.StatusCode;
                }

                if (response.IsSuccessStatusCode)
                {
                    string jsonResponse = await response.Content.ReadAsStringAsync();
                    string token = jsonResponse.Split(':', ',')[1];
                    return token;
                }
                else
                {
                    return "KO";
                }
                 
            }
            catch (ArgumentNullException e)
            {
                return e.Message;
            }
            catch (Exception e)
            {
                var message = await new MessageDialog(e.Message).ShowAsync();
                return message.ToString();
            }
        }
    }
}
