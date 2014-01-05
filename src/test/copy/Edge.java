package test.copy;

import java.awt.geom.Point2D;

public class Edge{
	// �G�b�W�͕��̃C���f�b�N�X�������Ă��āA�m�[�h�͐��̃C���f�b�N�X�������Ă���B
	static Integer nextEdgeIndex = -1;
	
	public Node source;// �eNode
	public Node target;// �qNode
	public double length;// �G�b�W�̒���
	
	public Integer index;
	
	public Edge() {
		this.index = nextEdgeIndex--;
		this.source = null;
		this.target = null;
		this.length = 60.0;
	}
	
	public void initWithSource(Node source_, Node target_, double length_) {
		this.source = source_;
		this.target = target_;
		// �G�b�W�̒����𒲐�����
		this.length = ((this.source.circleradius + this.target.circleradius) > 100) ? 100 : (this.source.circleradius + this.target.circleradius);
	}
	
	public double distanceToNode(Node node) {
		Support su = new Support();
		Point2D.Double n = su.PointNormalize( su.PointNormal( su.PointSubtract( this.target.position, this.source.position ) ) );
		Point2D.Double ac = su.PointSubtract(node.position, this.source.position);
		this.length = this.source.circleradius + this.target.circleradius;
		return Math.abs(ac.x * n.x + ac.y * n.y);
	}
}
