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
    public partial class Form1 : Form
    {
        //private Service srv;
        private ClientCtrl ctrl;
        private Voluntar loggedVoluntar;


        BindingSource bsCazuri = new BindingSource();
        BindingSource bsDonatori = new BindingSource();

        public Form1(ClientCtrl client, Voluntar voluntar)
        {
            InitializeComponent();
            this.loggedVoluntar = voluntar;
            this.ctrl = client;

            //IVoluntarRepo voluntarRepo = new VoluntarDBRepository();
            //IDonatorRepo donatorRepo = new DonatorDBRepository();
            //ICazRepo cazRepo = new CazDBRepository();
            //IDonatieRepo donatieRepo = new DonatieDBRepository();
            //srv = new Service(voluntarRepo, cazRepo, donatorRepo, donatieRepo);
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void populate()
        {
            
            bsCazuri.DataSource = ctrl.GetCazuri();
            dataGridView1.DataSource = bsCazuri;
            dataGridView1.Columns["ID"].Visible = false;
            dataGridView1.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;


            bsDonatori.DataSource = ctrl.GetDonatori();
            dataGridView2.DataSource = bsDonatori;
            dataGridView2.Columns["ID"].Visible = false;
            dataGridView2.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //bsCazuri.DataSource = srv.findAllCazuri();
            //dataGridView1.DataSource = bsCazuri;

            //bsDonatori.DataSource = srv.findAllDonatori();
            //dataGridView2.DataSource = bsDonatori;
            populate();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void dataGridView2_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                numeTextBox.DataBindings.Add("Text", bsDonatori, "Nume");
                prenumeTextBox.DataBindings.Add("Text", bsDonatori, "Prenume");
                AdresaTextBox.DataBindings.Add("Text", bsDonatori, "Adresa");
                telefonTextBox.DataBindings.Add("Text", bsDonatori, "Telefon");

            }
            catch (Exception ex)
            {

            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string nume="", prenume = "", adresa = "", telefon = "";
            int suma = 0;
            CazCaritabil currentObject = (CazCaritabil)dataGridView1.CurrentRow.DataBoundItem;
            if (currentObject == null)
            {
                MessageBox.Show("Nu ati ales nici un caz caritabil.");
                return;
            }
            
            try
            {

                if (sumaTextBox.Text.Length == 0 || numeTextBox.Text.Length == 0 || prenumeTextBox.Text.Length == 0 || telefonTextBox.Text.Length == 0 || AdresaTextBox.Text.Length == 0)
                    throw new Exception("Campurile nu au voie sa fie goale!");
                suma = int.Parse(sumaTextBox.Text);
                nume = numeTextBox.Text;
                prenume = prenumeTextBox.Text;
                adresa = AdresaTextBox.Text;
                telefon = telefonTextBox.Text;
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            //Donator donator = srv.findDonatorByNume(nume, prenume);
            //if(donator == null)
            //{
            //    srv.addDonator(nume, prenume, telefon, adresa);
            //    donator = srv.findDonatorByNume(nume, prenume);
            //    bsDonatori.DataSource = srv.findAllDonatori();
            //    dataGridView2.DataSource = bsDonatori;
            //}
            //srv.addDonatie(donator, currentObject, suma);
            //currentObject.SumaAdunata = currentObject.SumaAdunata + suma;
            //srv.updateCaz(currentObject);
            //bsCazuri.DataSource = srv.findAllCazuri();
            //dataGridView1.DataSource = bsCazuri;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
            Form2 form = new Form2(ctrl);
            form.Show();
        }

    

        private void searchTextBox_KeyPressed(object sender, EventArgs e)
        {
            String search = searchTextBox.Text;
            //List<Donator> rez = new List<Donator>();
            //srv.findAllDonatori().ToList().ForEach(x =>
            //{
            //    if (x.getNumeComplet().ToLower().Contains(search.ToLower()))
            //        rez.Add(x);
            //});
            //bsDonatori.DataSource = rez;
            //dataGridView2.DataSource = bsDonatori;
        }
    }
}
