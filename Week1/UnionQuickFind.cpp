#include <iostream>

class UQF
{
public:
	UQF(int size):
		m_size(size)
	{
		m_id = new int(m_size);
		for(int i = 0; i < m_size; i++)
		{
			m_id[i] = i;
		}
	}

	int Root(int a)
	{
		while(m_id[a] != a)
			a = m_id[a];

		return a;
	}
	
	void Union(int a, int b)
	{
		int rootA = Root(a);
		int rootB = Root(b);

		m_id[rootA] = rootB;
	}
	
	void Print()
	{
		for(int i = 0; i < m_size; i++)
		{
			std::cout << m_id[i] << " ";
		}
		std::cout << "\n";
	}

	bool Connected(int a, int b)
	{
		return Root(a) == Root(b);
	}

	
private:
	int* m_id;
	int m_size;
};
	

int main()
{
	UQF uf(10);
	uf.Print();
	uf.Union(1, 3);
	uf.Union(5, 6);
	uf.Print();
  
	std::cout << "6 connect with 5: " << uf.Connected(6, 5) << std::endl;
	std::cout << "6 connect with 4: " << uf.Connected(6, 4) << std::endl;

	return 0;
}
