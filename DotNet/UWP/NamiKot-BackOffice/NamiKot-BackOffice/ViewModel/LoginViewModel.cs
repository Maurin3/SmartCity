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

namespace NamiKot_BackOffice.ViewModel
{
    public class LoginViewModel : ViewModelBase
    {
        private string token;
        private ICommand goToDestinationCommand;
        private INavigationService navigationService;
        public Login Login { get; set; }

        public LoginViewModel(INavigationService navigationService = null)
        {
            this.navigationService = navigationService;
            string page = navigationService.CurrentPageKey;
            //Login = login;
        }

        public async Task GoToAdmin()
        {
            var service = new LoginService();
            Token = await service.GetToken(Login);
            navigationService.NavigateTo("HomePage");
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
                if(this.goToDestinationCommand == null)
                {
                    this.goToDestinationCommand = new RelayCommand(() => GoToAdmin());
                }
                return this.goToDestinationCommand;
            }
        }

        public ICommand GoToForgotPasswordCommand
        {
            get
            {
                if (this.goToDestinationCommand == null)
                {
                    this.goToDestinationCommand = new RelayCommand(() => GoToForgotPassword());
                }
                return this.goToDestinationCommand;
            }
        }

        public void GoToForgotPassword()
        {
            navigationService.NavigateTo("ForgotPasswordPage");
        }
    }
}
