package telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ModuloConexao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerListModel;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaCliente extends JFrame {
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JTextField t_nome;
	public String ids[] = new String[2];
	public JComboBox c_genero;
	private JLabel l_genero;
	private JComboBox c_filme;
	private JLabel l_filme;
	private JLabel l_valor;
	private JSpinner s_dias;
	private JLabel l_textPrazo;
	private JLabel l_prazo;
	private JRadioButton rb_detalhes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public void titulo(String genero) {
		String sql = "select filmes.tituloFilm, filmes.disponibFilm from filmes where generoFilm = (select idGenero from generos where genero = ?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, genero);
			rs = pst.executeQuery();
			while(rs.next()) {
				if(rs.getString(2).equals("Disponível")) {
					c_filme.addItem(rs.getObject(1));
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void preco(int dias) {
		String sql ="select preco from filmes where tituloFilm = ? ";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, c_filme.getSelectedItem().toString());
			rs = pst.executeQuery();
			if(rs.next()) {
				int x = dias;
				float total = rs.getFloat(1) * (x/5);
				l_valor.setText(Float.toString(total));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	public void idFilme() {
		String sql = "select idFilm from filmes where tituloFilm = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, c_filme.getSelectedItem().toString());
			rs = pst.executeQuery();
			if(rs.next()) {
				ids[1] = (rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	
	
	public void alugar(int d) {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("YYYY-MM-dd");
		String dataFormatada = formatar.format(dataAtual);
		dataAtual.setDate(dataAtual.getDate()+ d);
		String prazo = formatar.format(dataAtual);
		String sql = "insert into locacao (dataLoc, prazoDev, clienteLoc, filmeLoc) values (?, ?, ?, ?)";
		idFilme();
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, dataFormatada);
			pst.setString(2, prazo);
			pst.setString(3, ids[0]);
			pst.setString(4, ids[1]);
			pst.executeUpdate();
			c_genero.setSelectedItem("(Escolha:)");
			c_filme.removeAllItems();
			s_dias.setValue("0");
			l_valor.setText("0.0");
			l_prazo.setText("(Nenhum)");
			rb_detalhes.setVisible(false);
			
		} catch (Exception e) {
			
		}
	}
	
	
	
	public void mudaEstado() {
		String sql = "update filmes set disponibFilm = 'Ocupado' where idFilm = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, ids[1]);
			pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void mostraPrazo(int d) {
		Date dataAtual = new Date();
		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/YYYY");
		dataAtual.setDate(dataAtual.getDate()+d);
		String dataFormatada = formatar.format(dataAtual);
		if(d>0) {
			l_prazo.setText(dataFormatada);
		}
		else {
			l_prazo.setText("(Nenhum)");
		}
	}
	
	
	
	
	public void setNome(String nome) {
		t_nome.setText(nome);
	}

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		conexao = ModuloConexao.conector();
		setTitle("Alugue o seu filme!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton b_alugar = new JButton("Alugar");
		rootPane.setDefaultButton(b_alugar);
		
		t_nome = new JTextField();
		t_nome.setFont(new Font("Tahoma", Font.BOLD, 11));
		t_nome.setBounds(164, 52, 240, 20);
		contentPane.add(t_nome);
		t_nome.setColumns(10);
		t_nome.setEditable(false);
		
		JLabel l1 = new JLabel("Ol\u00E1, ");
		l1.setFont(new Font("Tahoma", Font.BOLD, 13));
		l1.setBounds(93, 54, 61, 14);
		contentPane.add(l1);
		
		c_genero = new JComboBox();
		c_genero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c_filme.setModel(new DefaultComboBoxModel(new String[] {"(Selecione:)"}));
				titulo(c_genero.getSelectedItem().toString());
				l_valor.setText("0.0");
				s_dias.setValue("0");
				rb_detalhes.setVisible(false);
			}
		});
		c_genero.setMaximumRowCount(10);
		c_genero.setModel(new DefaultComboBoxModel(new String[] {"(Escolha:)"}));
		c_genero.setFont(new Font("Tahoma", Font.BOLD, 11));
		c_genero.setBounds(39, 149, 143, 22);
		contentPane.add(c_genero);
		
		l_genero = new JLabel("Escolha o g\u00EAnero:");
		l_genero.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_genero.setBounds(61, 124, 108, 14);
		contentPane.add(l_genero);
		
		rb_detalhes = new JRadioButton("Detalhes do filme");
		rb_detalhes.setVisible(false);
		rb_detalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Detalhes detalhes = new Detalhes();
				idFilme();
				detalhes.setVisible(true);
				detalhes.getElenco(ids[1]);
				detalhes.getSinopse(ids[1]);
				detalhes.getDuracao(ids[1]);
				rb_detalhes.setSelected(false);
			}
		});
		rb_detalhes.setBounds(364, 178, 130, 23);
		contentPane.add(rb_detalhes);
		
		c_filme = new JComboBox();
		c_filme.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(c_filme.getSelectedItem().toString().equals("(Selecione:)")) {
					rb_detalhes.setVisible(false);
				}
				else {
					rb_detalhes.setVisible(true);
				}
				l_valor.setText("0.0");
				s_dias.setValue("0");
			}
		});
		c_filme.setBounds(336, 149, 184, 22);
		contentPane.add(c_filme);
		
		l_filme = new JLabel("Selecione o filme:");
		l_filme.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_filme.setBounds(364, 124, 101, 14);
		contentPane.add(l_filme);
		
		s_dias = new JSpinner();
		s_dias.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				preco(Integer.parseInt(s_dias.getValue().toString()));
				mostraPrazo(Integer.parseInt(s_dias.getValue().toString()));
			}
		});
		s_dias.setModel(new SpinnerListModel(new String[] {"0", "5", "10", "15", "20"}));
		JFormattedTextField ftf = ((JSpinner.DefaultEditor) s_dias.getEditor()).getTextField();
		ftf.setEditable(false);
		s_dias.setBounds(39, 251, 130, 20);
		contentPane.add(s_dias);
		
		
		b_alugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(c_genero.getSelectedItem().toString().equals("(Escolha:)") || 
				c_filme.getSelectedItem().toString().equals("(Selecione:)") || s_dias.getValue().toString().equals("0")) {
					
					JOptionPane.showMessageDialog(null, "Por favor, selecione todos os itens!", "Atenção!",0);
				}
				else {
					alugar(Integer.parseInt(s_dias.getValue().toString()));
					mudaEstado();
					JOptionPane.showMessageDialog(null, "Filme alugado com sucesso!", "Bom filme!", 1);
				}
			}
		});
		b_alugar.setBounds(232, 312, 89, 23);
		contentPane.add(b_alugar);
		
		JLabel lblEscolhaQuantosDias = new JLabel("Escolha quantos dias de uso:");
		lblEscolhaQuantosDias.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEscolhaQuantosDias.setBounds(22, 226, 160, 14);
		contentPane.add(lblEscolhaQuantosDias);
		
		JLabel l_textValor = new JLabel("Valor(R$) = ");
		l_textValor.setForeground(new Color(0, 153, 0));
		l_textValor.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_textValor.setBounds(336, 241, 69, 14);
		contentPane.add(l_textValor);
		
		l_valor = new JLabel("0.0");
		l_valor.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_valor.setForeground(new Color(0, 153, 0));
		l_valor.setBounds(415, 241, 105, 14);
		contentPane.add(l_valor);
		
		l_textPrazo = new JLabel("Prazo:");
		l_textPrazo.setForeground(Color.BLUE);
		l_textPrazo.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_textPrazo.setBounds(336, 266, 41, 14);
		contentPane.add(l_textPrazo);
		
		l_prazo = new JLabel("(Nenhum)");
		l_prazo.setForeground(Color.BLUE);
		l_prazo.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_prazo.setBounds(387, 266, 133, 14);
		contentPane.add(l_prazo);
	}
}



