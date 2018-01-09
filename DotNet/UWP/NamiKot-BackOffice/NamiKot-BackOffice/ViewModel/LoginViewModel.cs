using GalaSoft.MvvmLight;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NamiKot_BackOffice.Model;
using System.Windows.Input;
using GalaSoft.MvvmLight.Views;
using GalaSoft.MvvmLight.Command;
using NamiKot_BackOffice.View;

namespace NamiKot_BackOffice.ViewModel
{
    public class LoginViewModel : ViewModelBase, System.ComponentModel.INotifyPropertyChanged
    {
        private string token;
        private INavigationService NavigationService { get; set; }
        public Login Login { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }

        public LoginViewModel(INavigationService navigationService = null)
        {
            NavigationService = navigationService;
            string page = navigationService.CurrentPageKey;
        }

        public async Task GoToAdmin()
        {
            Login = new Login(UserName, Password);
            var service = new LoginService();
            Token = await service.GetToken(Login);
            NavigationService.NavigateTo("HomePage");
        }

        public string Token
        {
            get
            {
                return token;
            }
            set
            {
                token = value;
                RaisePropertyChanged("token");
            }
        }

        public ICommand GoToHomeCommand
        {
            get
            {
                return new RelayCommand(() => GoToAdmin());
            }
        }

        public ICommand GoToForgotPasswordCommand
        {
            get
            {
                return new RelayCommand(() => GoToForgotPassword());
            }
        }

        public void GoToForgotPassword()
        {
            NavigationService.NavigateTo("ForgotPasswordPage");
        }
    }
}
