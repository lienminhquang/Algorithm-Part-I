#include <iostream>

class UF
{
public:
	UF(int i_size)
		:m_size(i_size)
	{
		m_arr = new int[i_size];
		for(int i = 0; i < m_size; i++)
		{
			m_arr[i] = i;
		}
	}
	
	void Union(int a, int b)
	{
		int id_a = m_arr[a];
		int id_b = m_arr[b];
		if(id_a != id_b)
		{
			for(int i = 0; i < m_size; i++)
			{
				if(m_arr[i] == id_a)
				{
					m_arr[i] = id_b;
				}
			}
		}
	}
	void Print()
	{
		for(int i = 0; i < m_size; i++)
		{
			std::cout << m_arr[i] << " ";
		}
		std::cout << "\n";
	}

	bool Connected(int a, int b)
	{
		return m_arr[a] == m_arr[b];
	}
	
private:
	int *m_arr;
	int m_size;
};

int main()
{
	UF uf(10);
	uf.Print();
	uf.Union(1, 3);
	uf.Union(5, 6);
	uf.Print();
  
	std::cout << "6 connect with 5: " << uf.Connected(6, 5) << std::endl;
	std::cout << "6 connect with 4: " << uf.Connected(6, 4) << std::endl;
  
	return 0;
}
