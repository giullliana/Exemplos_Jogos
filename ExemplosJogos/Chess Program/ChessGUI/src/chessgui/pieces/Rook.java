package chessgui.pieces;

import chessgui.Board;

public class Rook extends Piece {

	public Rook(int x, int y, boolean is_white, String file_path, Board board)
	{
		super(x,y,is_white,file_path, board);
	}

	@Override
	public boolean canMove(int destX, int destY)
	{
		// Remember: A rook can move as many squares as he wants either forward,
		// backward, or sideways without jumping any pieces. He cannot attack
		// his own pieces.

		// WRITE CODE HERE
		Piece destPiece = board.getPiece(destX, destY);

		if (destPiece != null) 
		{
			if ((destPiece.isWhite() && this.isWhite()) || (!destPiece.isWhite() && !this.isWhite()))
			{
				return false;
			}
		}

		if ((this.getX() != destX) && (this.getY() != destY))
		{
			return false;
		}

		if (destY > this.getY())
		{
			int squaresMoved = Math.abs(destY - this.getY());
			for (int i = 1; i < squaresMoved; i++) 
			{
				Piece p = board.getPiece(this.getX(), this.getY() + i);
				if (p != null)
				{
					return false;
				}
			}
		}
		
		if (this.getY() > destY)
		{
			int squaresMoved = Math.abs(destY - this.getY());
			for (int i = 1; i < squaresMoved; i++) 
			{
				Piece p = board.getPiece(this.getX(), this.getY() - i);
				if (p != null)
				{
					return false;
				}
			}
		}
		
		if (destX > this.getX())
		{
			int squaresMoved = Math.abs(destX - this.getX());
			for (int i = 1; i < squaresMoved; i++) 
			{
				Piece p = board.getPiece(this.getX() + i, this.getY());
				if (p != null)
				{
					return false;
				}
			}
		}
		
		if (this.getX() > destX)
		{
			int squaresMoved = Math.abs(destX - this.getX());
			for (int i = 1; i < squaresMoved; i++) 
			{
				Piece p = board.getPiece(this.getX() - i, this.getY());
				if (p != null)
				{
					return false;
				}
			}
		}
		
		return true;
	}
}


