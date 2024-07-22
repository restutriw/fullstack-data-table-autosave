import React, { useState, useEffect } from 'react';
import { Container, Button, TextField, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import axios from 'axios';

const KaryawanTable = () => {
  const [data, setData] = useState([]);
  const [deletedIds, setDeletedIds] = useState([]);
  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    if (deletedIds.length > 0 || isSaving) {
      handleAutosave();
    }
    // eslint-disable-next-line
  }, [data, deletedIds, isSaving]);

  const fetchData = () => {
    axios.get('http://localhost:8080/api/karyawan')
      .then(response => {
        setData(response.data);
      })
      .catch(error => console.error('Error fetching data:', error));
  };

  const handleAutosave = async () => {
    const creates = data
      .filter(row => !row.id)
      .map(row => ({
        nama: row.nama,
        role: row.role,
        status_pekerjaan: row.status_pekerjaan
      }));

    const updates = data
      .filter(row => row.id)
      .map(row => ({
        id: row.id,
        karyawan: {
          nama: row.nama,
          role: row.role,
          status_pekerjaan: row.status_pekerjaan
        }
      }));

    const deletes = deletedIds;

    const bulkCRUDRequest = {
      creates,
      updates,
      deletes
    };

    try {
      await axios.post('http://localhost:8080/api/karyawan/bulk-crud', bulkCRUDRequest);
      setDeletedIds([]);  
      setIsSaving(false); 
      fetchData(); // Fetch data untuk update tabelnya
    } catch (error) {
      console.error('Error saving data:', error);
    }
  };

  const handleDelete = (id) => {
    setDeletedIds([...deletedIds, id]);
    setData(data.filter(row => row.id !== id));
    setIsSaving(true); 
  };

  const handleChange = (index, field, value) => {
    const newData = [...data];
    newData[index][field] = value;
    setData(newData);
    setIsSaving(true); 
  };

  const handleAddRow = () => {
    setData([...data, { nama: '', role: '', status_pekerjaan: '' }]);
    setIsSaving(true); 
  };

  return (
    <Container>
      <Button onClick={handleAddRow} variant="contained" color="secondary" style={{ margin: '20px' }}>
        Tambah Data
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nama</TableCell>
              <TableCell>Role</TableCell>
              <TableCell>Status Pekerjaan</TableCell>
              <TableCell>Aksi</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row, index) => (
              <TableRow key={row.id || index}>
                <TableCell>{row.id}</TableCell>
                <TableCell>
                  <TextField
                    value={row.nama}
                    onChange={(e) => handleChange(index, 'nama', e.target.value)}
                  />
                </TableCell>
                <TableCell>
                  <TextField
                    value={row.role}
                    onChange={(e) => handleChange(index, 'role', e.target.value)}
                  />
                </TableCell>
                <TableCell>
                  <TextField
                    value={row.status_pekerjaan}
                    onChange={(e) => handleChange(index, 'status_pekerjaan', e.target.value)}
                  />
                </TableCell>
                <TableCell>
                  <Button onClick={() => handleDelete(row.id)} color="secondary">Hapus</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default KaryawanTable;
