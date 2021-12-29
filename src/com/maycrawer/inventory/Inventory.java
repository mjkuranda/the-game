package com.maycrawer.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import com.maycrawer.display.GraphicsMain;
import com.maycrawer.gfx.Assets;
import com.maycrawer.handlers.Handler;
import com.maycrawer.handlers.MouseManager;
import com.maycrawer.sfx.Sounds;

public class Inventory {

	private Handler handler;
	
	private Item[][] items;
	private Item item_left, item_right;
	private Item select_item, collided_item;
	private int mx, my, mmx, mmy;
	
	private int x, y;
	private int width, height;
	
	public Inventory(GraphicsMain gMain, Handler handler) {
		this.handler = handler;
		
		this.x = gMain.getWidthScreen() / 2 - (768 / 2);
		this.y = gMain.getHeight() / 2 - 160;
		
		this.width = 20;
		this.height = 6;
		
		this.items = new Item[width][height];
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.inventory_texture, x, y, null);

		///////////////////////////-INVENTORY BACKGROUND-////////////////////////
		g.setColor(new Color(50, 50, 50));
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw3DRect(x, y, 768, 320, true);
		
		//////////////////////////-INVENTORY-TITLE-//////////////////////////////
		g.setColor(new Color(150, 150, 150, 150));
		g.setFont(Assets.font48);
		int xstr = x + (768 / 2) - (g.getFontMetrics().stringWidth("Inventory") / 2);
		int ystr = y + 20 + g.getFontMetrics().getHeight() / 2;
		g.drawString("Inventory", xstr, ystr);
		
		/////////////////////////-INVENTORY-INFO-////////////////////////////////
		g.setFont(Assets.font20);
		g.drawString("Total weight: " + getTotalWeight() + "kg", x + 16, y + 282);
		g.drawString("Empty slots: " + emptySlots(), x + 16, y + 306);
		
		///////////////////////////////////-OTHER-///////////////////////////////
		int item_x = x + 64;
		int item_y = y + 64;
		
		//-------------------WHEN-MOUSE-ON-THE-SLOT------------------------//
		MouseManager m = handler.getMouseManager();
		int xs = m.getXMoved() / 32 - (x / 32);
		int ys = m.getYMoved() / 32 - (y / 32);
		
		int col_item_x = ((this.mmx - this.x - 64) / 32);
		int col_item_y = ((this.mmy - this.y - 64) / 32);

		int x2s = this.mmx - this.x - 16;
		
		if(xs >= 2 && xs < 22 && ys >= 2 && ys < 8) {
			g.setColor(new Color(250, 250, 250, 100));
			g.fillRect(col_item_x * 32 + 2 + item_x, col_item_y * 32 + 3 + item_y, 28, 28);	
		} else if(x2s >= 0 && x2s < 32 && ys >= 2 && ys < 4) {
			g.setColor(new Color(250, 250, 250, 100));
			g.fillRect(x + 16 + 2, y + 3 + (ys * 32), 28, 28);
		}
		//-----------------------------------------------------------------//
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(items[i][j] != null) renderItem(g, items[i][j],
												item_x + i * 32, item_y + j * 32);
			}
		}
		
		if(this.item_left != null) {
			renderItem(g, this.item_left, item_x - 48, item_y);
		}
		
		if(this.item_right != null) {
			renderItem(g, this.item_right, item_x - 48, item_y + 32);
		}
		
		if(this.select_item != null) {
			renderItem(g, this.select_item, this.mmx - 16, this.mmy - 16);
		}
		
		if(this.collided_item != null) {
			renderItemInfo(g, this.collided_item);
		}
		
		if(x2s >= 0 && x2s < 32 && ys >= 2 && ys < 4) {
			if(ys == 2 && item_left != null) renderItemInfo(g, item_left);
			else if(ys == 3 && item_right != null) renderItemInfo(g, item_right);
		}
		
		//g.setColor(Color.RED);
		//g.fillRect(this.mx - 1, this.my - 1, 2, 2);
	}
	
	public void tick() {
		MouseManager m = handler.getMouseManager();
		
		this.mmx = m.getXMoved();
		this.mmy = m.getYMoved();
		
		int mouse_xx = (this.mmx - this.x - 64);
		int mouse_yy = (this.mmy - this.y - 64);
		
		int col_item_x = mouse_xx / 32;
		int col_item_y = mouse_yy / 32;
		
		if(isInItemsField(mouse_xx, mouse_yy) && this.mmx >= this.x + 64 && this.mmy >= this.y + 64) {
			if(items[col_item_x][col_item_y] != null) collided_item = items[col_item_x][col_item_y];
			else collided_item = null;	
		} else collided_item = null;
		
		if(m.isClicked()) {
			this.mx = m.getXClicked();
			this.my = m.getYClicked();
			
			int mouse_x = (this.mx - this.x - 64);
			int mouse_y = (this.my - this.y - 64);
			
			int field_items_x = mouse_x / 32;
			int field_items_y = mouse_y / 32;
			
			boolean left_click = (m.getButtonID() == 1);
			boolean right_click = (m.getButtonID() == 3);
			
			int previous_value = 0;
			
			if(isInItemsField(mouse_x, mouse_y) &&
					this.items[field_items_x][field_items_y] != null) {
				previous_value = this.items[field_items_x][field_items_y].getCount();	
			}
			
			////////////////////-LEFT-CLICK-AND-EMTPY-MOUSE-/////////////////////
			if(left_click && this.select_item == null) {
				if(isInItemsField(mouse_x, mouse_y)) {
					
					Item item = items[field_items_x][field_items_y];
					if(item != null) {
						this.select_item = new Item(item, item.getCount());
						this.items[field_items_x][field_items_y] = null;	
						Sounds.CLICK_W.play();
					}
					
				}
				
				else if(isOnLeftHand(mouse_x + 48, mouse_y + 64) && this.item_left != null) {
					
					this.select_item = new Item(this.item_left,
							this.item_left.getCount());
					this.item_left = null;
					Sounds.CLICK_W.play();
					
				}
				
				else if(isOnRightHand(mouse_x + 48, mouse_y + 96) &&  this.item_right != null) {
					
					this.select_item = new Item(this.item_right,
							this.item_right.getCount());
					this.item_right = null;
					Sounds.CLICK_W.play();
					
				}
			}
			else
			////////////////////-LEFT-CLICK-AND-FULL-MOUSE-//////////////////////
			if(left_click && this.select_item != null) {
				
				if(isInItemsField(mouse_x, mouse_y)) {
					
					Item item = this.items[field_items_x][field_items_y];
					
					if(hasSameID(item) && previous_value + this.select_item.getCount() <= Items.getStack(item)) {
						this.items[field_items_x][field_items_y] = new Item(this.select_item,
								previous_value + this.select_item.getCount());
						this.select_item = null;
					} else
					if(hasSameID(item) && previous_value + this.select_item.getCount() > Items.getStack(item)) {
						int amount = Items.getStack(item) - previous_value;
						this.items[field_items_x][field_items_y].addCount(amount);
						this.select_item.addCount(-1 * amount);
					} else
					if(!hasSameID(item)) {
						Item item1 = this.items[field_items_x][field_items_y];
						Item item2 = this.select_item;
						
						this.items[field_items_x][field_items_y] = new Item(item2, item2.getCount());
						this.select_item = new Item(item1, item1.getCount());
					}
					Sounds.CLICK_W.play();
					
				}
				
				else if(isOnLeftHand(mouse_x + 48, mouse_y + 64)) {
					
					Item item1 = this.item_left;
					Item item2 = this.select_item;
					
					int count1 = (item1 != null) ? item1.getCount() : 0;
					int count2 = (item2 != null) ? item2.getCount() : 0;
					
					this.item_left = (item2 != null) ? new Item(item2, count2) : null;
					this.select_item = (item1 != null) ? new Item(item1, count1) : null;
					
					Sounds.CLICK_W.play();
					
				}
				
				else if(isOnRightHand(mouse_x + 48, mouse_y + 96)) {
					
					Item item1 = this.item_right;
					Item item2 = this.select_item;
					
					int count1 = (item1 != null) ? item1.getCount() : 0;
					int count2 = (item2 != null) ? item2.getCount() : 0;
					
					this.item_right = (item2 != null) ? new Item(item2, count2) : null;
					this.select_item = (item1 != null) ? new Item(item1, count1) : null;
					
					Sounds.CLICK_W.play();
					
				}
				
			}
			else
			//////////////////-RIGHT-CLICK-AND-EMPTY-MOUSE-//////////////////////
			if(right_click && this.select_item == null &&
					isInItemsField(mouse_x, mouse_y)) {
				
				Item item = this.items[field_items_x][field_items_y];
				
				if(item != null) {
					//this.select_item = new Item(item, count + ((item.getCount() % 2 == 1) ? 1 : 0));
					int count = item.getCount() / 2 + 1;
					int prev = item.getCount();
					if(count > 1) {
						this.select_item = new Item(item, count);
						this.items[field_items_x][field_items_y] = new Item(item, prev - count);
						Sounds.CLICK_W.play();
						return;
					}

				}
				
			}
			///////////////////-RIGHT-CLICK-AND-FULL-MOUSE-//////////////////////
			if(right_click && this.select_item != null &&
					isInItemsField(mouse_x, mouse_y)) {
				
				Item item = this.items[field_items_x][field_items_y];
		
				if(hasSameID(item) && this.select_item.getCount() > 1) {
					
					if(item != null) {
						if(previous_value + 1 <= Items.getStack(item)) {
							this.items[field_items_x][field_items_y].addOne();
							this.select_item.takeOne();
						}
					} else {
						this.items[field_items_x][field_items_y] = new Item(this.select_item,
								previous_value + 1);
						this.select_item.takeOne();
					}
					Sounds.CLICK_W.play();
					
				} else {
					m.clicked();
					return;
				}
			}
			//////////////////////////////-END-//////////////////////////////////
			
			m.clicked();
		}
	}
	
	public boolean isInItemsField(int field_items_x, int field_items_y) {
		if(field_items_x >= 0 && field_items_x < this.width * 32) {
			if(field_items_y >= 0 && field_items_y < this.height * 32) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasSameID(Item item) {
		return ((item == null) || (this.select_item.getId() == item.getId()));
	}

	public void addNewItem(Item item, int count) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] == null) {
					items[x][y] = new Item(item, count);
					return;
				}
			}
		}
	}
	
	public void addItem(Item item, int count) {
		
		int c = count;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null && items[x][y].getId() == item.getId()) {
					
					byte n = (byte) (Items.getStack(item) - items[x][y].getCount());
					if(c > n) {
						items[x][y].addCount(n);
						c -= n;
					} else {
						items[x][y].addCount(c);
						return;	
					}
				}
				
				if(c == 0) return;
			}
		}
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] == null) {
//					items[x][y] = new Item(item, count);
//					return;
					byte n = (byte) (Items.getStack(item));
					if(c > n) {
						items[x][y] = new Item(item, n);
						c -= n;
					} else {
						items[x][y] = new Item(item, c);
						return;	
					}
				}
				if(c == 0) return;
			}
		}
		
	}
	
	public int emptySlots() {
		int empties = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] == null) {
					empties++;
				}
			}
		}
		return empties;
	}
	
	public void renderItem(Graphics g, Item item, int x, int y) {
		
		g.drawImage(item.getTexture(), x, y, 32, 32, null);
		
		if(item.getCount() > 1) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Consolas", Font.PLAIN, 18));
			int count_width = g.getFontMetrics().stringWidth("" + item.getCount());
			g.drawString("" + item.getCount(), x + 31 - count_width, y + 30);	
		}
	}
	
	private void renderItemInfo(Graphics g, Item item) {
		g.setColor(new Color(100, 100, 100, 200));
		g.fillRect(mmx, mmy, 160, 84);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.BOLD, 16));
		g.drawString(item.getName(), mmx, mmy + 16);
		g.setFont(new Font("Consolas", Font.PLAIN, 16));
		g.drawString("Amount: " + item.getCount(), mmx, mmy + 32);
		g.drawString("Weight: " + (item.getWeight() * item.getCount()) + " [kg]", mmx, mmy + 48);
		g.drawString("Stack : " + Items.getStack(item), mmx, mmy + 64);
		g.drawString("Type  : " + item.getType().getName(), mmx, mmy + 80);
	}
	
	public void resetInventory() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				items[x][y] = null;
			}
		}
	}
	
	public void setRandomInventory() {
		Random r = new Random();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(r.nextInt(8) == 0) {
					Item item = Items.items[r.nextInt(Items.ITEMS_COUNT)];
					int count = r.nextInt(Items.getStack(item)) + 1;
					items[x][y] = new Item(item, count);
				}
			}
		}
	}
	
	public void takeItem(Item item, int count) {
		int amount = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null && items[x][y].getId() == item.getId()) {
					int a = items[x][y].getCount();
					if(amount + a <= count) {
						items[x][y] = null;
						amount += a;
					}
					else if(amount + a > count) {
						int i = (amount + a) - count;
						items[x][y] = new Item(item, i);
						return;
					}
					if(amount == count) return;
				}
			}
		}
	}
	
	public boolean hasItem(Item item) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null && items[x][y].getId() == item.getId()) return true;
			}
		}
		return false;
	}
	
	public int getCount(Item item) {
		int count = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null && items[x][y].getId() == item.getId())
					count += items[x][y].getCount();
			}
		}
		
		return count;
	}
	
	public Item getWholeItem(Item item) {
		int count = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null && items[x][y].getId() == item.getId())
					count += items[x][y].getCount();
			}
		}
		
		if(count > 0)
			return new Item(item, count);
		else return null;
	}
	
	private boolean isOnLeftHand(int xm, int ym) {
		return (xm >= 0 && xm < 32 && ym >= 64 && ym < 96);
	}
	
	private boolean isOnRightHand(int xm, int ym) {
		return (xm >= 0 && xm < 32 && ym >= 128 && ym < 160);
	}
	
	public Item getItemLeftHand() {
		return item_left;
	}
	
	public Item getItemRightHand() {
		return item_right;
	}
	
	public void setItemLeftHand(Item item) {
		this.item_left = item;
	}
	
	public void setItemRightHand(Item item) {
		this.item_right = item;
	}
	
	public Item getSelectedItem() {
		return select_item;
	}
	
	public void setSelectItem(Item item) {
		this.select_item = item;
	}
	
	private float getTotalWeight() {
		float weight = 0.0f;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(items[x][y] != null) {
					int count = items[x][y].getCount();
					float wei = items[x][y].getWeight();
					weight += (count * wei);
				}
			}
		}
		return weight;
	}
	
	public void takeItemFromMainHand(boolean mainHand, int count) {
		Item item = (mainHand) ? item_right : item_left;
		
		if(item != null) {
			if(item.getCount() > 0 && item.getCount() >= count) {
				
				if(mainHand) {
					item_right.addCount(-1 * count);
					if(item_right.getCount() <= 0) item_right = null;
				}
				else {
					item_left.addCount(-1 * count);
					if(item_left.getCount() <= 0) item_left = null;
				}
				
			}
		}
	}
	
//	private String getLeftItem(int xm, int ym) {
//		if(xm >= x + 16 && xm < x + 32 && ym >= y + 64 && ym < y + 96)
//			return "Item left!";
//		return "null";
//	}

}
