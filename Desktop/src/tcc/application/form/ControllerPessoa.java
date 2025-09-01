/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcc.application.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;
import raven.toast.Notifications;
import tcc.application.Application;
import tcc.application.form.other.FormMainMenu;
import tcc.application.model.Clinica;
import tcc.application.model.Horarios;
import tcc.application.model.Medico;
import tcc.application.model.Pessoa;
import tcc.application.model.dao.BCryptUtil;
import tcc.application.model.dao.DaoClinica;
import tcc.application.model.dao.DaoHorario;
import tcc.application.model.dao.DaoPessoa;

/**
 *
 * @author Borges
 */
public class ControllerPessoa {

    private Pessoa pessoa;
    private Medico medico;
    private FormManager formManager;
    private LoginMedicoForm loginMedicoForm;
    private LoginClinicaForm loginClinicaForm;
    private Application app;
    private DaoPessoa daoPessoa;
    private DaoHorario daoHorario;
    private DaoClinica daoClinica;
    private Medico medicoSelecionado;
    private tcc.application.model.dao.BCryptUtil bCrypt;
    private FormMainMenu fmm;

    public ControllerPessoa() {
        pessoa = new Pessoa();
        medico = new Medico();
        formManager = new FormManager();
        loginMedicoForm = new LoginMedicoForm();
        loginClinicaForm = new LoginClinicaForm();
        daoPessoa = new DaoPessoa();
        daoClinica = new DaoClinica();
        daoHorario = new DaoHorario();
        bCrypt = new BCryptUtil();
        app = new Application();

    }

    public void cadastrarMedico(String crm, String email, String nome, String senha, String cpf, LocalDate dataNa, String tipo) {
        Pessoa p = new Medico(crm, daoClinica.selecionar(), email, nome, bCrypt.hashSenha(senha), cpf, dataNa, tipo);

        if (daoPessoa.inserir(p)) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                    "Medico inserido com sucesso. ");

        } else {
             Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
                    "Erro ao inserir Medico");
        }
    }

    public boolean verificarLoginMedico(String crm, String senha) {
        if (daoPessoa.validarLogin(crm, senha)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarLoginClinica(String nome, String senha) {
        if (daoClinica.validarLogin(nome, senha)) {
            return true;
        } else {
            return false;
        }
    }

}
