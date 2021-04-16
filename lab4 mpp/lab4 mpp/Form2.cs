using teledon.domain;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace teledon.client
{
    public partial class Form2 : Form
    {
        private ClientCtrl ctrl;

        public Form2(ClientCtrl client)
        {
            InitializeComponent();
            this.ctrl = client;

            //IVoluntarRepo voluntarRepo = new VoluntarDBRepository();
            //IDonatorRepo donatorRepo = new DonatorDBRepository();
            //ICazRepo cazRepo = new CazDBRepository();
            //IDonatieRepo donatieRepo = new DonatieDBRepository();
            //srv = new Service(voluntarRepo, cazRepo, donatorRepo, donatieRepo);

       
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                //Voluntar voluntar = srv.findVoluntar(usernameTextBox.Text);
                //if (voluntar == null)
                //    throw new ArgumentException("Nu a fost gasit nici un utilizator cu acel nume");

                //if (!voluntar.Password.Equals(passwordTextBox.Text))
                //    throw new ArgumentException("Parola gresita.");

                Console.WriteLine("CTRL login");
                ctrl.login(usernameTextBox.Text, passwordTextBox.Text);
                Form1 form = new Form1(ctrl, new Voluntar(usernameTextBox.Text, passwordTextBox.Text));
                form.Show();
                usernameTextBox.Clear();
                passwordTextBox.Clear();
                this.Hide();


            }
            catch (Exception ex)
            {
                label3.Text = ex.Message;
            }
        }


    }
}
