package telas2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.ModuloConexao;
import java.sql.*;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaConsulta extends JFrame {
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTable table;
	private JTextField t_pesqNome;
	private JLabel l_pesqNome;
	private JTextField t_nome;
	private JTextField t_cpf;
	private JTextField t_nasc;
	private JTextField t_tel;
	private JTextField t_cad;
	private JTextField t_filme;
	private JTextField t_prazo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsulta frame = new TelaConsulta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public void vazio() {
		String sql = "select cliente.nomeCliente as Nome, cliente.cpfCliente as Cpf, cliente.telCliente as telefone, \r\n" + 
				"date_format(cliente.dataNasc, '%d-%m-%Y') as Nascimento, \r\n" + 
				"date_format(cliente.dataCad, '%d-%m-%Y') as Cadastro, \r\n" + 
				"filmes.tituloFilm as Filme, date_format(locacao.prazoDev, '%d-%m-%Y') as Prazo\r\n" + 
				"from cliente left join locacao \r\n" + 
				"on cliente.idCliente = locacao.clienteLoc left join filmes on locacao.filmeLoc=filmes.idFilm\r\n" + 
				"where cliente.idCliente = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, "0");
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	public void tudo() {
		String sql = "select cliente.nomeCliente as Nome, cliente.cpfCliente as Cpf, cliente.telCliente as telefone, \r\n" + 
				"date_format(cliente.dataNasc, '%d-%m-%Y') as Nascimento, \r\n" + 
				"date_format(cliente.dataCad, '%d-%m-%Y') as Cadastro, \r\n" + 
				"filmes.tituloFilm as Filme, date_format(locacao.prazoDev, '%d-%m-%Y') as Prazo\r\n" + 
				"from cliente left join locacao \r\n" + 
				"on cliente.idCliente = locacao.clienteLoc left join filmes on locacao.filmeLoc=filmes.idFilm";
		try {
			pst = conexao.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void getClienteFilme() {
		String sql = "select cliente.nomeCliente as Nome, cliente.cpfCliente as Cpf, cliente.telCliente as telefone, \r\n" + 
				"date_format(cliente.dataNasc, '%d-%m-%Y') as Nascimento, \r\n" + 
				"date_format(cliente.dataCad, '%d-%m-%Y') as Cadastro, \r\n" + 
				"filmes.tituloFilm as Filme, date_format(locacao.prazoDev, '%d-%m-%Y') as Prazo\r\n" + 
				"from cliente left join locacao \r\n" + 
				"on cliente.idCliente = locacao.clienteLoc left join filmes on locacao.filmeLoc=filmes.idFilm\r\n" + 
				"where cliente.nomeCliente like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, t_pesqNome.getText() + "%");
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	

	/**
	 * Create the frame.
	 */
	public TelaConsulta() {
		conexao = ModuloConexao.conector();
		setTitle("Pesquisar cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 356);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 324, 713, 223);
		contentPane.add(panel);
		panel.setVisible(false);
		panel.setLayout(null);
		
		JLabel l_nome = new JLabel("Nome:");
		l_nome.setBounds(10, 39, 46, 14);
		l_nome.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(l_nome);
		
		t_nome = new JTextField();
		t_nome.setBounds(98, 36, 177, 20);
		panel.add(t_nome);
		t_nome.setColumns(10);
		t_nome.setEditable(false);
		
		JLabel l_cpf = new JLabel("Cpf:");
		l_cpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_cpf.setBounds(375, 39, 70, 14);
		panel.add(l_cpf);
		
		t_cpf = new JTextField();
		t_cpf.setBounds(414, 36, 172, 20);
		panel.add(t_cpf);
		t_cpf.setColumns(10);
		t_cpf.setEditable(false);
		
		JLabel l_nasc = new JLabel("Nascimento:");
		l_nasc.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_nasc.setBounds(343, 87, 70, 14);
		panel.add(l_nasc);
		
		t_nasc = new JTextField();
		t_nasc.setBounds(98, 84, 177, 20);
		panel.add(t_nasc);
		t_nasc.setColumns(10);
		t_nasc.setEditable(false);
		
		JLabel l_tel = new JLabel("Telefone:");
		l_tel.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_tel.setBounds(10, 87, 78, 14);
		panel.add(l_tel);
		
		t_tel = new JTextField();
		t_tel.setBounds(414, 84, 172, 20);
		panel.add(t_tel);
		t_tel.setColumns(10);
		t_tel.setEditable(false);
		
		JLabel l_cad = new JLabel("Cadastro:");
		l_cad.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_cad.setBounds(10, 135, 61, 14);
		panel.add(l_cad);
		
		t_cad = new JTextField();
		t_cad.setBounds(98, 132, 177, 20);
		panel.add(t_cad);
		t_cad.setColumns(10);
		t_cad.setEditable(false);
		
		JLabel l_filme = new JLabel("Filme:");
		l_filme.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_filme.setBounds(358, 135, 46, 14);
		panel.add(l_filme);
		
		t_filme = new JTextField();
		t_filme.setBounds(414, 132, 172, 20);
		panel.add(t_filme);
		t_filme.setColumns(10);
		t_filme.setEditable(false);
		
		JLabel l_prazo = new JLabel("Prazo de entrega:");
		l_prazo.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_prazo.setBounds(140, 186, 128, 14);
		panel.add(l_prazo);
		
		t_prazo = new JTextField();
		t_prazo.setBounds(255, 185, 177, 20);
		panel.add(t_prazo);
		t_prazo.setColumns(10);
		t_prazo.setEditable(false);		
		
		
		JLabel l1 = new JLabel("");
		l1.setForeground(new Color(0, 153, 51));
		l1.setBounds(537, 11, 146, 14);
		contentPane.add(l1);
		if(conexao!=null) {
			l1.setText("Conexão bem-sucedida!");
		}
		else {
			l1.setText("Sem conexão!");
			l1.setForeground(Color.RED);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 152, 713, 161);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				setBounds(100,100, 749, 606);
				
				int a = table.getSelectedRow();
				
				panel.setVisible(true);
				
				if (table.getModel().getValueAt(a, 0)!=null) {
					t_nome.setText(table.getModel().getValueAt(a, 0).toString());
				}
				else {
					t_nome.setText("");
				}
				if (table.getModel().getValueAt(a, 1)!=null) {
					t_cpf.setText(table.getModel().getValueAt(a, 1).toString());
				}
				else {
					t_cpf.setText("");
				}
				if (table.getModel().getValueAt(a, 2)!=null) {
					t_nasc.setText(table.getModel().getValueAt(a, 2).toString());
				}
				else {
					t_nasc.setText("");
				}
				if (table.getModel().getValueAt(a, 3)!=null) {
					t_tel.setText(table.getModel().getValueAt(a, 3).toString());
				}
				else {
					t_tel.setText("");
				}
				if (table.getModel().getValueAt(a, 4)!=null) {
					t_cad.setText(table.getModel().getValueAt(a, 4).toString());
				}
				else {
					t_cad.setText("");
				}
				if(table.getModel().getValueAt(a, 5)!=null) {
					t_filme.setText(table.getModel().getValueAt(a, 5).toString());
				}
				else {
					t_filme.setText("");
				}
				if(table.getModel().getValueAt(a, 6)!=null) {
					t_prazo.setText(table.getModel().getValueAt(a, 6).toString());
				}
				else {
					t_prazo.setText("");
				}
		
			}
		});
		table.setForeground(Color.BLUE);
		table.setBackground(Color.GREEN);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Cpf", "Nascimento", "Telefone", "Cadastro", "Filme", "Prazo"
			}
		));
		JTableHeader coluna = table.getTableHeader();
		coluna.setFont(new Font("Tahoma", Font.BOLD, 13));
		scrollPane.setViewportView(table);
		
	
		l_pesqNome = new JLabel("Digite o nome:");
		l_pesqNome.setForeground(Color.CYAN);
		l_pesqNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_pesqNome.setBounds(135, 69, 139, 14);
		contentPane.add(l_pesqNome);
		
		t_pesqNome = new JTextField();
		t_pesqNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(t_pesqNome.getText().equals("")) {
					vazio();
				}
				else if (t_pesqNome.getText().equals("*")) {
					tudo();
				}
				else {
					getClienteFilme();
				}
			}
		});
		t_pesqNome.setBounds(239, 66, 235, 20);
		contentPane.add(t_pesqNome);
		t_pesqNome.setColumns(10);
	}
}
