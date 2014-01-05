package test;

import java.awt.geom.Point2D;
import java.awt.*;
import java.awt.image.*;


public class Node{

	// �m�[�h�͐��̃C���f�b�N�X�������Ă��āA�G�b�W�͕��̃C���f�b�N�X�������Ă���B
	static int nextNodeIndex = 1;
	
	public String			name;	  // �L�[���[�h
	public double			mass;	  
	public Point2D.Double	position; // ���W
	public boolean			fixed;	  // �Œ艻
	public Integer			like;	  // ������
	public Integer			index;	  // ID
	public Point2D.Double	scale;	  // ������̒����i��, ���a�j
	public Point2D.Double	bottomright, bottomleft, topright, topleft;
	public double 			circleradius;// �~�̔��a
	
	public Node() {
		this.index = nextNodeIndex++;
		this.name = null;
		this.mass = 1.0;
		this.like = 0;
		this.position = new Point2D.Double(0, 0);
		this.fixed = false;
		this.scale = new Point2D.Double(1, 1);// �~�̑傫��
		this.circleradius = 1.0;// �~�̔��a
		this.bottomleft = new Point2D.Double(0, 0);
		this.bottomright = new Point2D.Double(0, 0);
		this.topleft = new Point2D.Double(0, 0);
		this.topright = new Point2D.Double(0, 0);
	}
	
	// �����̕����擾���A�~�̔��a�����߂�
	public void Length() {
		Support su = new Support();
		BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
 
		String data = this.name;
		// �t�H���g�̓��C���I�ŁA�����̃T�C�Y��10���炢���˂̒l�ɂ���đ傫���Ȃ��Ă���
		g2d.setFont(new Font("���C���I", Font.PLAIN, 14 + (int)(this.like * 1.2)));
		FontMetrics fm = g2d.getFontMetrics();
		int width = 0;
		// �����v�Z
		for(int j=0; j<data.length(); j++)
		{
			width += fm.charWidth(data.charAt(j));
		}
		
		this.scale.x = width; this.scale.y = fm.getHeight();
		this.bottomright.x = this.scale.x; this.bottomright.y = -this.scale.y;
		this.bottomleft.x = -this.scale.x; this.bottomleft.y = -this.scale.y;
		this.topright.x = this.scale.x; this.topright.y = this.scale.y;
		this.topleft.x = -this.scale.x; this.topleft.y = this.scale.y;
		// ���a�v�Z
		this.circleradius = su.CircleRadius(this.topleft, this.bottomleft, this.bottomright, this.topright) + 1;
	}
	
	// �����˃{�^�������ꂽ�Ƃ��ɌĂԁB�܂��A�����˂̏���l��10�Ƃ���
	public void PlusLike() {
		if(this.like <= 10) {
			this.like++;
			this.circleradius += (this.like * 1.2);	
		}
	}
	
	public Node initWithName(String name_, Double mass_, Point2D.Double position_, boolean fixed_, Integer like_) {
		this.name = name_;
		this.mass = mass_;
		this.position = position_;
		this.fixed = fixed_;
		this.like = like_;
		this.Length();
		return this;	
	}
}
