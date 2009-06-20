package edu.ur.ir.institution;

import java.sql.Timestamp;
import java.util.Date;

import edu.ur.ir.IndexProcessingType;
import edu.ur.persistent.BasePersistent;

/**
 * Processing record for an institutional item
 * 
 * @author Nathan Sarr
 *
 */
public class InstitutionalItemIndexProcessingRecord extends BasePersistent{
	
	/** Eclipse generated id */
	private static final long serialVersionUID = -1641115075589242260L;

	/** Institutional item id to be processed */
	private Long institutionalItemId;
	
	/** Type of processing to be completed on the record */
	private IndexProcessingType indexProcessingType;
	
	/** Date the record was last updated */
	private Timestamp updatedDate;
	
	/**
	 * Package protected constructor
	 */
	InstitutionalItemIndexProcessingRecord(){}
	
	/**
	 * Create an institutional item index processing record with the given 
	 * item id and processing type.
	 * 
	 * @param institutionalItemId
	 * @param indexProcessingType
	 */
	public InstitutionalItemIndexProcessingRecord(Long institutionalItemId, IndexProcessingType indexProcessingType)
	{
		setInstitutionalItemId(institutionalItemId);
		setIndexProcessingType(indexProcessingType);
		updatedDate = new Timestamp(new Date().getTime());
	}

	/**
	 * Get the institutional item id.
	 * 
	 * @return institutional item id.
	 */
	public Long getInstitutionalItemId() {
		return institutionalItemId;
	}

	/**
	 * Set the institutional item id.
	 * 
	 * @param institutionalItemId
	 */
	void setInstitutionalItemId(Long institutionalItemId) {
		this.institutionalItemId = institutionalItemId;
	}

	/**
	 * Get the index processing type.
	 * 
	 * @return
	 */
	public IndexProcessingType getIndexProcessingType() {
		return indexProcessingType;
	}

	/**
	 * Set the index processing type.
	 * 
	 * @param indexProcessingType
	 */
	void setIndexProcessingType(IndexProcessingType indexProcessingType) {
		this.indexProcessingType = indexProcessingType;
	}
	
	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int value = 0;
		value += indexProcessingType == null ? 0 : indexProcessingType .hashCode();
		value += institutionalItemId == null ? 0 : institutionalItemId.hashCode();
		return value;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InstitutionalItemIndexProcessingRecord)) return false;

		final InstitutionalItemIndexProcessingRecord other = (InstitutionalItemIndexProcessingRecord) o;

		if( ( indexProcessingType != null && !indexProcessingType.equals(other.getIndexProcessingType()) ) ||
			( indexProcessingType == null && other.getIndexProcessingType() != null ) ) return false;
		
		if( ( institutionalItemId != null && !institutionalItemId.equals(other.getInstitutionalItemId()) ) ||
			( institutionalItemId == null && other.getInstitutionalItemId() != null ) ) return false;

		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("[Institutional Item Index Record  id = ");
		sb.append(id);
		sb.append(" index processing type= ");
		sb.append(indexProcessingType);
		sb.append(" institutionalItemId = ");
		sb.append(institutionalItemId);
		sb.append("]");
		return sb.toString();
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
