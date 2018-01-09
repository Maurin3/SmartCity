using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NamiKot_BackOffice.ViewModel
{
    public class HomeViewModel : ViewModelBase, System.ComponentModel.INotifyPropertyChanged
    {
        private INavigationService navigationService;

        public HomeViewModel(INavigationService navigationService = null)
        {
            this.navigationService = navigationService;
        }


    }
}
