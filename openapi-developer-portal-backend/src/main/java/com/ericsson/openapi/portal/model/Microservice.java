package com.ericsson.openapi.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "microservice")
public class Microservice
{

	@Id
	private String id;
	private String title;
	private String description;
	private String category;
	private String name;
	private String email;
	private Date date;
	private String version;
	private List<String> dependencies;
	private String specification;

	public Microservice(
			String title, String description, String category, String name, String email, Date date,
			String version, List<String> dependencies, String specification)
	{
		this.title = title;
		this.description = description;
		this.category = category;
		this.name = name;
		this.email = email;
		this.date = date;
		this.version = version;
		this.dependencies = dependencies;
		this.specification = specification;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setDate()
	{
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();

		Calendar lowerBound = Calendar.getInstance();
		lowerBound.set(Calendar.YEAR, 2020);
		Date startDate = lowerBound.getTime();

		long range = currentDate.getTime() - startDate.getTime();

		SecureRandom secureRandom = new SecureRandom();

		long randomTimeOffset = secureRandom.nextLong(range);
		long randomTimeInMillis = startDate.getTime() + randomTimeOffset;

		Date randomDate = new Date(randomTimeInMillis);

		date = randomDate;
	}

	public void setDate(String date)
	{
		if(Objects.equals(date, ""))
		{
			setDate();
			return;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try
		{
			this.date = formatter.parse(date);
		}
		catch(ParseException e)
		{

		}
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public void setDependencies(List<String> dependencies)
	{
		this.dependencies = dependencies;
	}

	public void setSpecification(String specification)
	{
		this.specification = specification;
	}

}
