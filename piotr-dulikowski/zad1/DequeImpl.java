public class DequeImpl
{
	public static final int MIN_CAPACITY = 16;
	private int[] els;
	int arrSize;
	int nEls;
	int ptrL, ptrR;
	
	//Zwiêksza b¹dŸ zmniejsza rozmiar tablicy tak, aby by³o jeszcze wolne miejsce
	private final void adjustSize()
	{
		int size;
		if (nEls == arrSize)
			size = arrSize * 2;
		else if (arrSize > MIN_CAPACITY && nEls * 4 <= arrSize)
			size = arrSize / 2;
		else
			return;
		
		//System.out.println("DBG: resizing " + arrSize + " -> " + size);
		
		int[] nuArr = new int[size];
		
		//3 mo¿liwoœci
		if (ptrL < ptrR)
		{
			//Elementy s¹ w przedziale [ptrL, ptrR-1]
			//Kopiujemy na pocz¹tek tablicy
			for (int i = ptrL; i < ptrR; i++)
				nuArr[i-ptrL] = els[i];
			
			ptrR -= ptrL;
			ptrL = 0;
		}
		else if (nEls > 0)
		{
			//Elementy s¹ przy brzegach
			int diff = size - arrSize;
			
			//Kopiujemy przedzia³ [0, ptrR-1]
			for (int i = 0; i < ptrR; i++)
				nuArr[i] = els[i];
			
			//Kopiujemy przedzia³ [ptrL, arrSize-1]
			for (int i = ptrL; i < arrSize; i++)
				nuArr[i+diff] = els[i];
			
			ptrL += diff;
		}
		//W przeciwnym przypadku kolejka jest pusta?
		
		els = nuArr;
		arrSize = size;
	}
	
	public DequeImpl()
	{
		arrSize = MIN_CAPACITY;
		ptrL = 0;
		ptrR = 0;
		nEls = 0;
		els = new int[MIN_CAPACITY];
	}
	
	public void pushLeft(int el)
	{
		adjustSize();
		
		ptrL--;
		if (ptrL < 0)
			ptrL += arrSize;
		
		els[ptrL] = el;
		
		nEls++;
	}
	
	public void pushRight(int el)
	{
		adjustSize();
		
		els[ptrR] = el;
		
		ptrR++;
		if (ptrR >= arrSize)
			ptrR -= arrSize;
		
		nEls++;
	}
	
	public int popLeft()
	{
		if (nEls == 0)
			return -1;
		
		int ret = els[ptrL];
		ptrL++;
		if (ptrL >= arrSize)
			ptrL -= arrSize;
		
		nEls--;
		adjustSize();
		
		return ret;
	}
	
	public int popRight()
	{
		if (nEls == 0)
			return -1;
		
		ptrR--;
		if (ptrR < 0)
			ptrR += arrSize;
		
		int ret = els[ptrR];
		
		nEls--;
		adjustSize();
		
		return ret;
	}
	
	public int at(int pos)
	{
		//Zak³adam, ¿e indeksujemy elementy od ptrL
		if (pos < 0 || pos >= nEls)
			return -1;
		
		pos += ptrL;
		if (pos >= arrSize)
			pos -= arrSize;
		
		return els[pos];
	}
	
	public int size()
	{
		return nEls;
	}
	
	public boolean isEmpty()
	{
		return (nEls == 0);
	}
	
	public int hasCycle()
	{
		int elid = at(0);
		int counter = nEls;
		
		while (counter > 0)
		{
			if (elid == -1)
				return -1;
			
			counter--;
			elid = at(elid);
		}
		
		if (elid == -1)
			return -1;
		
		//Wiemy, ¿e jest cykl, zatem szukamy jego d³ugoœci
		int elid2 = elid;
		
		do
		{
			counter++;
			elid2 = at(elid2);
		}	while (elid != elid2);
		
		return counter;
	}
	
	public static void main(String args[])
	{
		DequeImpl di = new DequeImpl();
		
		di.pushRight(1);
		di.pushRight(2);
		di.pushRight(3);
		di.pushRight(4);
		di.pushRight(0);
		
		System.out.println("Has cycle of length " + di.hasCycle());
	}
};
