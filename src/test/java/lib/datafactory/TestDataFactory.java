package lib.datafactory;

public class TestDataFactory {
	
	public static MyDataProvider createData(DataSourceType type)
	{
		switch(type)
		{
		case EXCEL:
			return new ExcelDataProvider();
		case FAKER:
			return new FakerDataProvider();
		default:
			throw new IllegalArgumentException("Wrong Data source type");
			
		}
	}
	
	

}
