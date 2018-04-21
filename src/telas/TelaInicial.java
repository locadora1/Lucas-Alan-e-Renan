package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.sql.*;
import dao.ModuloConexao;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import telas2.TelaConsulta;

public class TelaInicial extends JFrame {
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTextField t_login;
	private JPasswordField t_senha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	int a =1;
	private ArrayList lista = new ArrayList();
	
	
	public void populaLista() {
		String sql = "select genero from generos";
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			if(a<2) {
				while(rs.next()) {
					lista.add(rs.getObject(1));
				}
			}
			a=a+1;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void loginSenha() {
		String sql = "select * from cliente where login = ? and senha = ?";
		TelaCliente cliente = new TelaCliente();
		try {
			populaLista();
			pst = conexao.prepareStatement(sql);
			pst.setString(1, t_login.getText());
			pst.setString(2, t_senha.getText());
			rs = pst.executeQuery();
			
			if(t_login.getText().equals("Admin") && t_senha.getText().equals("admin")) {
				TelaConsulta consulta = new TelaConsulta();
				consulta.setVisible(true);
				fechar();
				conexao.close();
			}
			else {
				if(rs.next()) {
					for(int i=0; i<lista.size(); i++) {
						cliente.c_genero.addItem(lista.get(i));
					}
					
					cliente.setVisible(true);
					cliente.ids[0] =(rs.getString(1));
					cliente.setNome(rs.getString(2));
					fechar();
					conexao.close();
				}
				else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos!", "Atenção!", 0);
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void fechar() {
		this.dispose();
	}
	

	/**
	 * Create the frame.
	 */
	public TelaInicial() {
		conexao = ModuloConexao.conector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton b_entrar = new JButton("Entrar");
		rootPane.setDefaultButton(b_entrar);
		
		
		JLabel l_conexao = new JLabel("");
		l_conexao.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_conexao.setForeground(new Color(0, 153, 0));
		l_conexao.setBounds(10, 11, 157, 14);
		contentPane.add(l_conexao);
		if(conexao!=null) {
			l_conexao.setText("Conexão bem-sucedida!");
		}
		else {
			l_conexao.setForeground(Color.RED);
			l_conexao.setText("Sem conexão!");
			JOptionPane.showMessageDialog(null, "Erro ao estabelecer conexão!", "Atenção!", 0);
		}
		
		t_login = new JTextField();
		t_login.setBounds(135, 81, 169, 20);
		contentPane.add(t_login);
		t_login.setColumns(10);
		
		JLabel l_login = new JLabel("Login:");
		l_login.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_login.setBounds(37, 84, 68, 14);
		contentPane.add(l_login);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSenha.setBounds(37, 138, 46, 14);
		contentPane.add(lblSenha);
		
		
		b_entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginSenha();
			}
		});
		b_entrar.setBounds(166, 201, 89, 23);
		contentPane.add(b_entrar);
		
		t_senha = new JPasswordField();
		t_senha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int a = t_senha.getText().length();
				if(a>=8) {
					t_senha.setText(t_senha.getText().substring(0, 8));
				}
			}
		});
		t_senha.setBounds(135, 135, 169, 20);
		contentPane.add(t_senha);
		
		JButton b_cad = new JButton("Cadastrar!");
		b_cad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(conexao!=null) {
					TelaCadastro cad = new TelaCadastro();
					cad.setVisible(true);
					fechar();
				}
				else {
					JOptionPane.showMessageDialog(null, "Sem conexão!", "Erro", 0);
				}
			}
		});
		b_cad.setBounds(321, 11, 103, 20);
		contentPane.add(b_cad);
	}
}

