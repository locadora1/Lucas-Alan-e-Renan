package telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ModuloConexao;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class TelaCadastro extends JFrame {
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTextField t_nome;
	private JTextField t_cpf;
	private JTextField t_tel;
	private JTextField t_dia;
	private JTextField t_login;
	private JPasswordField t_senha;
	private JPasswordField t_conf;
	private JTextField t_cad;
	private JTextField t_mes;
	private JTextField t_ano;
	private JLabel l_igual;
	private int x = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String dataCadTela() {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("               dd/MM/YYYY");
		String dataFormatada = formatar.format(dataAtual);
		return(dataFormatada);
	}
	
	public String dataCadBanco() {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("YYYY-MM-dd");
		String dataFormatada = formatar.format(dataAtual);
		return(dataFormatada);
	}
	
	public int anoMinimo() {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("y");
		int ano = Integer.parseInt(formatar.format(dataAtual))-118;
		return(ano);
	}
	
	public int anoMaximo() {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("y");
		int ano = Integer.parseInt(formatar.format(dataAtual))-10;
		return(ano);
	}
	
	
	public boolean cadastra() {
		String sql = "insert into cliente (nomeCliente, cpfCliente, telCliente, dataNasc, dataCad, login, senha) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, t_nome.getText());
			pst.setString(2, t_cpf.getText());
			pst.setString(3, t_tel.getText());
			pst.setString(4, t_ano.getText() + "-" + t_mes.getText() + "-" + t_dia.getText());
			pst.setString(5, dataCadBanco());
			pst.setString(6, t_login.getText());
			pst.setString(7, t_senha.getText());
			pst.executeUpdate();
			return(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Data inválida!", "Atenção!", 0);
			return(false);
		}
	}
	
	public boolean isExisting() {
		String sql = "select * from cliente where login = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, t_login.getText());
			rs = pst.executeQuery();
			if(rs.next()) {
				
				return(true);
			}
			else {
				return(false);
			}
		} catch (Exception e) {
			
			return(false);
		}
	}
	
	
	public boolean isExisting2() {
		String sql = "select * from cliente where cpfCliente = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, t_cpf.getText());
			rs = pst.executeQuery();
			if(rs.next()) {
				return(true);
			}
			else {
				return(false);
			}
		} catch (Exception e) {
			
			return(false);
		}
	}
	
	
	
	private void fechar() {
		this.dispose();
	}
	
	

	/**
	 * Create the frame.
	 */
	public TelaCadastro() {
		conexao = ModuloConexao.conector();
		setTitle("Cadastre-se");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton b_cad = new JButton("Cadastrar");
		rootPane.setDefaultButton(b_cad);
		
		t_nome = new JTextField();
		t_nome.setBounds(102, 51, 163, 20);
		contentPane.add(t_nome);
		t_nome.setColumns(10);
		
		JLabel l_nome = new JLabel("Nome:");
		l_nome.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_nome.setBounds(47, 54, 97, 14);
		contentPane.add(l_nome);
		
		t_cpf = new JTextField();
		t_cpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				int a = t_cpf.getText().length();
				if(a>=11) {
					t_cpf.setText(t_cpf.getText().substring(0, 11));
				}
				try {
					long b = Long.parseLong(t_cpf.getText());
				} catch (Exception e) {
					if (evt.getKeyCode()!=8) {
						JOptionPane.showMessageDialog(null, "Digite apenas números para este campo!", "Atenção!", 0);
						t_cpf.setText(t_cpf.getText().substring(0, t_cpf.getText().length()-1));
					}
				}
			}
		});
		t_cpf.setToolTipText("Somente os n\u00FAmeros!");
		t_cpf.setBounds(102, 109, 163, 20);
		contentPane.add(t_cpf);
		t_cpf.setColumns(10);
		
		JLabel l_cpf = new JLabel("Cpf:");
		l_cpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_cpf.setBounds(47, 112, 97, 14);
		contentPane.add(l_cpf);
		
		JLabel l_tel = new JLabel("Telefone:");
		l_tel.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_tel.setBounds(353, 54, 58, 14);
		contentPane.add(l_tel);
		
		t_tel = new JTextField();
		t_tel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				int a = t_tel.getText().length();
				if(a>=11) {
					t_tel.setText(t_tel.getText().substring(0, 11));
				}
				try {
					long b = Long.parseLong(t_tel.getText());
				} catch (Exception e) {
					if (evt.getKeyCode()!=8) {
						JOptionPane.showMessageDialog(null, "Digite apenas números para este campo!", "Atenção!", 0);
						t_tel.setText(t_tel.getText().substring(0, t_tel.getText().length()-1));
					}
				}
			}
		});
		t_tel.setToolTipText("Apenas n\u00FAmeros!");
		t_tel.setBounds(421, 51, 163, 20);
		contentPane.add(t_tel);
		t_tel.setColumns(10);
		
		JLabel l_nasc = new JLabel("Nascimento:");
		l_nasc.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_nasc.setBounds(335, 112, 76, 14);
		contentPane.add(l_nasc);
		
		t_dia = new JTextField();
		t_dia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				int a = t_dia.getText().length();
				if(a>=2) {
					t_dia.setText(t_dia.getText().substring(0, 2));
				}
				
				try {
					int b = Integer.parseInt(t_dia.getText());
					if(b>31 || t_dia.getText().equals("00")) {
						t_dia.setText(t_dia.getText().substring(0, t_dia.getText().length()-1));
					}
				} catch (Exception e) {
					if (evt.getKeyCode()!=8) {
						JOptionPane.showMessageDialog(null, "Digite apenas números para este campo!", "Atenção!", 0);
						t_dia.setText(t_dia.getText().substring(0, t_dia.getText().length()-1));
					}
				}
				
			}
		});
		t_dia.setToolTipText("dd");
		t_dia.setBounds(421, 109, 35, 20);
		contentPane.add(t_dia);
		t_dia.setColumns(10);
		
		t_login = new JTextField();
		t_login.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int a = t_login.getText().length();
				if(a>=15) {
					t_login.setText(t_login.getText().substring(0, 15));
				}
			}
		});
		t_login.setBounds(91, 180, 174, 20);
		contentPane.add(t_login);
		t_login.setColumns(10);
		
		JLabel l_login = new JLabel("Crie um login:");
		l_login.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_login.setBounds(130, 155, 90, 14);
		contentPane.add(l_login);
		
		JCheckBox ch_senha = new JCheckBox("Ver Senha");
		ch_senha.setFont(new Font("Tahoma", Font.BOLD, 11));
		ch_senha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(x%2==0) {
					t_senha.setEchoChar('\u0000');
					x=x+1;
				}
				else {
					t_senha.setEchoChar('*');
					x=x+1;
				}
			}
		});
		ch_senha.setBounds(515, 179, 97, 23);
		contentPane.add(ch_senha);
		ch_senha.setVisible(false);
		
		t_senha = new JPasswordField();
		t_senha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int a = t_senha.getText().length();
				if(a>=8) {
					t_senha.setText(t_senha.getText().substring(0, 8));
				}
				if(t_senha.getText().equals("")) {
					ch_senha.setVisible(false);
					t_conf.setText("");
					l_igual.setText("");
				}
				else {
					ch_senha.setVisible(true);
				}
			}
		});
		t_senha.setEchoChar('*');
		t_senha.setToolTipText("No m\u00E1ximo, 8 d\u00EDgitos!");
		t_senha.setBounds(335, 180, 174, 20);
		contentPane.add(t_senha);
		
		
		JLabel l_senha = new JLabel("Crie uma senha:");
		l_senha.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_senha.setBounds(374, 155, 97, 14);
		contentPane.add(l_senha);
		
		t_conf = new JPasswordField();
		t_conf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(t_conf.getText().equals(t_senha.getText())) {
					l_igual.setForeground(new Color(0, 153, 0));
					l_igual.setText("OK!");
				}
				else {
					l_igual.setForeground(Color.RED);
					l_igual.setText("X!");
				}
			}
		});
		t_conf.setBounds(335, 261, 174, 20);
		contentPane.add(t_conf);
		
		JLabel l_conf = new JLabel("Confirme sua senha:");
		l_conf.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_conf.setBounds(365, 236, 118, 14);
		contentPane.add(l_conf);
		
		t_cad = new JTextField();
		t_cad.setBounds(91, 261, 174, 20);
		contentPane.add(t_cad);
		t_cad.setColumns(10);
		t_cad.setText(dataCadTela());
		t_cad.setEditable(false);
		
		JLabel l_cad = new JLabel("Data de cadastramento:");
		l_cad.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_cad.setBounds(109, 236, 145, 14);
		contentPane.add(l_cad);
		
		
		b_cad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(t_nome.getText().equals("")||t_tel.getText().equals("")||t_cpf.getText().equals("")||t_dia.getText().equals("")
					||t_mes.getText().equals("")||t_ano.getText().equals("")||t_senha.getText().equals("")||t_conf.getText().equals("")) {
					
					JOptionPane.showMessageDialog(null, "Por Favor, preencha todos os campos!", "Atenção!", 0);
				}
				
				else if (t_cpf.getText().length()<11 || (t_tel.getText().length()<11 && t_tel.getText().length()!=9)) {
		
					JOptionPane.showMessageDialog(null, "Cpf e/ou telefone incompletos!", "Atenção!", 0);

				}
				
				else if(t_ano.getText().length()!=4) {
					JOptionPane.showMessageDialog(null, "O campo ano deve ter 4 dígitos!", "Atenção!", 0);
				}	
				
				else if (isExisting()) {
					JOptionPane.showMessageDialog(null, "Este login já existe!", "Atenção!", 0);
				}
				
				else if(isExisting2()) {
					JOptionPane.showMessageDialog(null, "Este CPF já está cadastradado!", "Atenção!", 0);
				}
				
				else if (t_senha.getText().equals(t_conf.getText())) {
					
					TelaInicial inicial = new TelaInicial();
					
					boolean isCadastrado = cadastra();
					
					if(isCadastrado) {
						l_igual.setText("");
						
						JOptionPane.showMessageDialog(null, "Cadastramento realizado com sucesso!", "Agradecemos seu cadastro!", 1);
							
						inicial.setVisible(true);
							
						fechar();
					}
					
				}
				else {
					
					JOptionPane.showMessageDialog(null, "Confirme sua senha corretamente!","Atenção!",0);
				}
			}
		});
		b_cad.setBounds(272, 330, 111, 23);
		contentPane.add(b_cad);
		
		JLabel lblOlSigaOs = new JLabel("Ol\u00E1, siga os campos abaixo:");
		lblOlSigaOs.setForeground(Color.BLUE);
		lblOlSigaOs.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOlSigaOs.setBounds(241, 11, 170, 14);
		contentPane.add(lblOlSigaOs);
		
		t_mes = new JTextField();
		t_mes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				int a = t_mes.getText().length();
				if(a>=2) {
					t_mes.setText(t_mes.getText().substring(0, 2));
				}
				
				try {
					int b = Integer.parseInt(t_mes.getText());
					if(b>12 || t_mes.getText().equals("00")) {
						t_mes.setText(t_mes.getText().substring(0, t_mes.getText().length()-1));
					}
				} catch (Exception e) {
					if (evt.getKeyCode()!=8) {
						JOptionPane.showMessageDialog(null, "Digite apenas números para este campo!", "Atenção!", 0);
						t_mes.setText(t_mes.getText().substring(0, t_mes.getText().length()-1));
					}
				}
			}
		});
		t_mes.setBounds(476, 109, 35, 20);
		contentPane.add(t_mes);
		t_mes.setColumns(10);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(522, 112, 23, 14);
		contentPane.add(label_1);
		
		t_ano = new JTextField();
		t_ano.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				int a = t_ano.getText().length();
				if(a>=4) {
					t_ano.setText(t_ano.getText().substring(0, 4));
				}
				
				try {
					int b = Integer.parseInt(t_ano.getText());
					if(t_ano.getText().equals("0") || t_ano.getText().equals("00") || t_ano.getText().equals("000") || 
					t_ano.getText().equals("0000")) {
						t_ano.setText("");
					}
					if(t_ano.getText().length()==4 && (Integer.parseInt(t_ano.getText())<anoMinimo() || 
					Integer.parseInt(t_ano.getText())>anoMaximo())) {
						t_ano.setText("");
					}
				} catch (Exception e) {
					if (evt.getKeyCode()!=8) {
						JOptionPane.showMessageDialog(null, "Digite apenas números para este campo!", "Atenção!", 0);
						t_ano.setText(t_ano.getText().substring(0, t_ano.getText().length()-1));
					}
				}
			}
		});
		t_ano.setToolTipText("yyyy");
		t_ano.setBounds(532, 109, 46, 20);
		contentPane.add(t_ano);
		t_ano.setColumns(10);
		
		JLabel l_dia = new JLabel("Dia");
		l_dia.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_dia.setBounds(427, 95, 29, 14);
		contentPane.add(l_dia);
		
		JLabel l_mes = new JLabel("M\u00EAs");
		l_mes.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_mes.setBounds(480, 95, 29, 14);
		contentPane.add(l_mes);
		
		JLabel l_ano = new JLabel("Ano");
		l_ano.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_ano.setBounds(542, 95, 29, 14);
		contentPane.add(l_ano);
		
		JLabel label = new JLabel("/");
		label.setBounds(466, 112, 5, 14);
		contentPane.add(label);
		
		l_igual = new JLabel("");
		l_igual.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_igual.setBounds(532, 267, 123, 14);
		contentPane.add(l_igual);
	}
}

